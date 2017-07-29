package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rayfay.bizcloud.platforms.apigateway.swagger.controller.SBaseRequest;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SearchApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplication;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplicationApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.mongo.ApiDocs;
import com.rayfay.bizcloud.platforms.apigateway.swagger.mongo.ApiFullText;
import com.rayfay.bizcloud.platforms.apigateway.swagger.mongo.SwaggerDocumentRepo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.IApiAccreditAppRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.SwaggerApisRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.SwaggerApplicationRepository;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by stzhang on 2017/3/2.
 */
@Service
public class SwaggerParserServiceImpl implements ISwaggerParserService{
    @Autowired
    protected SwaggerApisRepository apisRepository;
    @Autowired
    protected SwaggerApplicationRepository applicationRepository;
    @Autowired
    private EurakeFounder founder;

    @Autowired
    private SwaggerDocumentRepo documentRepo;
    
	@Autowired
	@PersistenceContext
	private EntityManager em;
	

    @Override
    public void synSwaggerDataFromAppServer(String appName){
        String apiDocsUrl = founder.find(appName);
        Swagger swagger = swaggerParser(apiDocsUrl);
        SwaggerApplication swaggerApplication = retrieveSwaggerApplication(appName, swagger);
        ApiDocs apiDocs = new ApiDocs();
        apiDocs.setFileId(swaggerApplication.getFileId());
        apiDocs.setJsonData(Json.pretty(swagger));
        //save document json
        documentRepo.saveDocument(apiDocs);
        List<SwaggerApplicationApis> apisList = retrieveSwaggerApplicationApis(swaggerApplication,  swagger);
        // save application
        applicationRepository.save(swaggerApplication);
        // save apis.
        apisRepository.deleteAllApiByApp(appName);
        apisRepository.save(apisList);
        //save to mongodb.
        removeAllByAppNameFromMongodb(appName);
        saveApiRepositoryToMongoDb(apisList);
    }

    @Override
    public String queryApiDocs(String pathId, SBaseRequest sbr){
        SwaggerApplicationApis applicationApis =  apisRepository.findOne(pathId);
        String appName = applicationApis.getAppName();
        SwaggerApplication application  = applicationRepository.findOne(appName);
        String fileId = application.getFileId();
        ApiDocs apiDocs = documentRepo.getOneDocument(fileId);
        if(sbr != null && sbr.getBasePath() == null){
            sbr.setBasePath("/hosts/"+appName);
        }
        String apiJson = getPathSwaggerMeta(apiDocs.getJsonData(), applicationApis.getPath(), sbr);
        return apiJson;
    }

    public List<SearchApis> searchSwaggerApis_bakup(String text) {
        return null;
        // return documentRepo.fullSearch(text);
    }

    @Override
    public List<SearchApis> searchSwaggerApis(String text) {
        String likeText = "%" + text + "%";
        List<SearchApis> res = Lists.newArrayList();
        List<SwaggerApplicationApis> apis  = apisRepository.searchApiByText(likeText);
        if(apis != null){
            for (SwaggerApplicationApis api : apis) {
                 res.add(SearchApis.convertSwaggerApplicationApi2(api));
            }
        }
        return res;
    }

    @Override
    public List<ApiInfo> searchSwaggerApisPageable(int pageSize,int pageNumber,String queryStr) {
        String likeText = "%" + queryStr + "%";
        int startNum=pageSize*pageNumber;
		String sql = "SELECT * FROM (   " +
                " SELECT  a.id as apiId,a.api_path as apiPath,a.app_name as appName ,a.summary,a.tags,a.description,a.method,a.path,a.swagger_app_id as swaggerAppId ,group_concat(d.type_name separator ',') as typeName,group_concat(c.dummy_app_id separator ',') as dummyAppId  " +
				" FROM t_swagger_application_apis a  "+
                " left JOIN type_apis b on a.api_path=b.api_path "+
                " LEFT JOIN (SELECT a.api_type_id,group_concat(a.dummy_app_id separator ',') as dummy_app_id FROM t_api_accredit_app a GROUP BY a.api_type_id) c on b.type_id=c.api_type_id  "+
                " LEFT JOIN api_type d on b.type_id=d.id  ";
		
		
		if(StringUtils.isNotEmpty(queryStr)){
			sql = sql + " WHERE a.path like ? or a.app_name like ? or a.description like ? or a.summary like ? or a.tags like ? or a.method like ?   ";
		}
		    sql = sql + "GROUP BY a.api_path ) t "+
		          "LIMIT "+startNum+", "+pageSize+"";
		
		Query query = em.createNativeQuery(sql);
		
		
		
		if(StringUtils.isNotEmpty(queryStr)){
				query.setParameter(1, likeText);
				query.setParameter(2, likeText);
				query.setParameter(3, likeText);
				query.setParameter(4, likeText);
				query.setParameter(5, likeText);
				query.setParameter(6, likeText);

		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean( ApiInfo.class));
	
		List<ApiInfo> rows =(List<ApiInfo>)query.getResultList();
		return rows;
    }


    private String getPathSwaggerMeta(String swaggerJson, String path, SBaseRequest sbr){
        SwaggerParser parser = new SwaggerParser();
        Swagger swagger = parser.parse(swaggerJson);
        if(sbr != null) {
            swagger.setHost(sbr.getHost());
            swagger.setBasePath(sbr.getBasePath());
        }
        Path currentPath = swagger.getPaths().get(path);
        Map<String, Path > swaggerPaths = Maps.newHashMap();
        swaggerPaths.put(path, currentPath);
        swagger.setPaths(swaggerPaths);
        return Json.pretty(swagger);
    }

    // some private methods
    protected Swagger swaggerParser(String url){
        SwaggerParser parser = new SwaggerParser();
        Swagger swagger = parser.read(url);
        return swagger;
    }

    private SwaggerApplication retrieveSwaggerApplication(String appName, Swagger swagger){
        SwaggerApplication swaggerApplication = new SwaggerApplication();
        swaggerApplication.setAppName(appName);
        swaggerApplication.setBasePath(swagger.getBasePath());
        swaggerApplication.setDescription(swagger.getInfo().getDescription());
        swaggerApplication.setTitle(swagger.getInfo().getTitle());
        swaggerApplication.setVersion(swagger.getInfo().getVersion());
        swaggerApplication.setFileId("fileId://"+appName);
        return swaggerApplication;
    }

    private List<SwaggerApplicationApis> retrieveSwaggerApplicationApis(SwaggerApplication swaggerApp, Swagger swagger){
        List<SwaggerApplicationApis> apis = Lists.newArrayList();
        Map<String, Path> swaggerPaths = swagger.getPaths();
        Iterator<Map.Entry<String, Path>> it = swaggerPaths.entrySet().iterator();
        Map<String, Operation>  operationMap = new HashMap<>();
        while (it.hasNext()){
            Map.Entry<String, Path>  entry = it.next();
            Path p = entry.getValue();
            if(p.getGet() != null){
                operationMap.put("GET",p.getGet());
            }
            if(p.getPost() != null){
                operationMap.put("POST",p.getPost());
            }
            if(p.getHead() != null){
                operationMap.put("HEAD",p.getHead());
            }
            if(p.getDelete() != null){
                operationMap.put("DELETE",p.getDelete());
            }
            if(p.getOptions() != null){
                operationMap.put("OPTION",p.getOptions());
            }
            if(p.getPut() != null){
                operationMap.put("PUT",p.getPut());
            }
            if(p.getPatch() != null){
                operationMap.put("PATCH",p.getPatch());
            }
            Iterator<Map.Entry<String, Operation>> opeIt = operationMap.entrySet().iterator();
            while(opeIt.hasNext())
            {
                Map.Entry<String, Operation>  opeEntry = opeIt.next();
                Operation singleOperation = opeEntry.getValue();
                SwaggerApplicationApis swaggerApplicationApis = new SwaggerApplicationApis();
                swaggerApplicationApis.setSwaggerAppId(swaggerApp.getId());
                swaggerApplicationApis.setAppName(swaggerApp.getAppName());
                swaggerApplicationApis.setPath(entry.getKey());
                swaggerApplicationApis.setDescription(singleOperation.getDescription());
                swaggerApplicationApis.setSummary(singleOperation.getSummary());
                swaggerApplicationApis.setTags(singleOperation.getTags());
                swaggerApplicationApis.setMethod(opeEntry.getKey());
                apis.add(swaggerApplicationApis);
            }
            operationMap.clear();
        }
        return apis;

    }




    /**
     * save to mongodb.
     * @param apisList
     */
    private void saveApiRepositoryToMongoDb(List<SwaggerApplicationApis> apisList){
         if(apisList != null){
             for (SwaggerApplicationApis applicationApis : apisList) {
                 ApiFullText apiFullText = convertSwaggerApplicationApi(applicationApis);
                 documentRepo.saveDocument(apiFullText);
             }
         }
    }

    private void removeAllByAppNameFromMongodb(String appName){
        documentRepo.findAllAndRemoveByAppName(appName);
    }


    private ApiFullText convertSwaggerApplicationApi(SwaggerApplicationApis api){
        ApiFullText apiFullText = new ApiFullText();
        apiFullText.setId(api.getId());
        apiFullText.setAppName(api.getAppName());
        apiFullText.setDescription(api.getDescription());
        apiFullText.setPath(api.getPath());
        apiFullText.setTags(StringUtils.join(api.getTags(), ","));
        apiFullText.setSummary(api.getSummary());
        return apiFullText;
    }


    public void setApisRepository(SwaggerApisRepository apisRepository) {
        this.apisRepository = apisRepository;
    }

    public void setApplicationRepository(SwaggerApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public void setFounder(EurakeFounder founder) {
        this.founder = founder;
    }

    public void setDocumentRepo(SwaggerDocumentRepo documentRepo) {
        this.documentRepo = documentRepo;
    }
}
