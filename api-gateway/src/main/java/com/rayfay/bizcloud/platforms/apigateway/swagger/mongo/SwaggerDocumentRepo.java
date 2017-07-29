package com.rayfay.bizcloud.platforms.apigateway.swagger.mongo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by stzhang on 2017/3/2.
 */
@Component
public class SwaggerDocumentRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ApiDocs getOneDocument(String fileId){
        ApiDocs docs = mongoTemplate.findById(fileId, ApiDocs.class);
        return docs;
    }

    public String saveDocument(ApiDocs docs){
        mongoTemplate.save(docs);
        return docs.getFileId();
    }

    public String saveDocument(ApiFullText docs){
        mongoTemplate.save(docs);
        return docs.getId();
    }

    public void removeDocument(ApiDocs docs){
        mongoTemplate.remove(docs);
    }

    public void  findAllAndRemoveByAppName(String appName){
        Criteria criteria = Criteria.where("appName").is(appName);
        Query query =  Query.query(criteria);
        mongoTemplate.findAllAndRemove(query, ApiFullText.class);
    }

    public List<ApiFullText> fullSearch(String searchText){
        Query query = new Query();
        if(StringUtils.isNotBlank(searchText)){
            TextCriteria textCriteria = TextCriteria.forLanguage(UsingTextIndexLanguages.usingLanguageName).matching(searchText);
            query = TextQuery.queryText(textCriteria).sortByScore().with(new PageRequest(0, 20));
        }
        List<ApiFullText> fullResult = mongoTemplate.find(query, ApiFullText.class);
        return fullResult;
    }


}
