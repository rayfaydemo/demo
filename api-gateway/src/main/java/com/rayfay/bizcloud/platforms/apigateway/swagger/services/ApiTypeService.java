package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.TypeApisInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.IApiTypeRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.BizCloudSorter;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.PageRequestMakerUtil;

/**
 * Created by shenfu on 2017/3/31.
 */
@Service("ApiTypeService")
public class ApiTypeService {

	private final IApiTypeRepository apiTypeRepository;
	
	@Autowired
	@PersistenceContext
	private EntityManager em;

	@Autowired
	public ApiTypeService(IApiTypeRepository apiTypeRepository) {
		this.apiTypeRepository = apiTypeRepository;
	}

	public ApiType findOne(String id) {
		return apiTypeRepository.findOne(id);
	}

	public ApiType insertApiType(ApiType apiType) {
		return apiTypeRepository.save(apiType);

	}

	public void delApiType(String id) {
		apiTypeRepository.delete(id);
		;
	}

	/**
	 * 根据查询条件查询接口分类
	 * 
	 * @param typeName
	 * @return 接口分类集合
	 */
	public List<ApiType> findApiType(String typeName) {

		List<ApiType> list = apiTypeRepository.findAll(new Specification<ApiType>() {
			@Override
			public Predicate toPredicate(Root<ApiType> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (StringUtils.isNotEmpty(typeName)) {
					predicates.add(criteriaBuilder.like(root.get("typeName"), "%"+typeName+"%"));
				}
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		return list;
	}


	
	public boolean exists(String typeName, String typeId) {
		// 查找指名称的分类
		ApiType apiType = apiTypeRepository
				.findOne((root, criteriaQuery, criteriaBuilder) -> {
					List<Predicate> predicates = new ArrayList<>();
					if (StringUtils.isNotEmpty(typeName)) {
						predicates.add(criteriaBuilder.equal(root.get("typeName"), typeName));
					}
					if (StringUtils.isNotEmpty(typeId)) {
						predicates.add(criteriaBuilder.notEqual(root.get("id"), typeId));
					}
					return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
				});

		// 可以获得
		if (apiType != null) {
			// 应用存在
			return true;
		} else {
			// 应用不存在
			return false;
		}
	}
	

	/**
	 * 根据查询条件查询人员身份信息(分页)
	 * @param pageSize 每页数据数
	 * @param pageNumber 当前页数
	 * @param queryMap 查询条件集合
	 * @return 人员身份信息集合
	 */
	public Page<ApiType> findApiTypePageable(int pageSize, int pageNumber,String typeName,List<BizCloudSorter> sorter) {
			
		PageRequest pageRequest = PageRequestMakerUtil.makePageRequest(pageNumber, pageSize, sorter);
		Page<ApiType> page = apiTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotEmpty(typeName)) {
				predicates.add(criteriaBuilder.like(root.get("typeName"), "%"+typeName+"%"));
			}

			return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageRequest);
		return page;	
	}

	
	
	
	
	public List<TypeApisInfo> getTypeDetailList(String typeId,String queryStr)
	{
		String sql = "SELECT A.type_id as typeId,B.id as apiId,B.api_path as apiPath ,B.app_name as appName,B.description,B.path,B.summary,B.swagger_app_id as swaggerAppId,B.tags,B.method  " +
                " FROM type_apis A ,t_swagger_application_apis B " +
                " WHERE A.api_path=B.api_path ";
		
		sql = sql + " AND A.type_id = ? ";
		if(StringUtils.isNotEmpty(queryStr)){
			sql = sql + " AND (B.path like ? or B.app_name like ? or b.description like ? or summary like ? or tags like ? or method like ?) ";
		}
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, typeId);
		
		if(StringUtils.isNotEmpty(queryStr)){
			query.setParameter(2, "%"+queryStr+"%");
			query.setParameter(3, "%"+queryStr+"%");
			query.setParameter(4, "%"+queryStr+"%");
			query.setParameter(5, "%"+queryStr+"%");
			query.setParameter(6, "%"+queryStr+"%");
			query.setParameter(7, "%"+queryStr+"%");
		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean( TypeApisInfo.class));
	
		List<TypeApisInfo> rows =(List<TypeApisInfo>)query.getResultList();
		return rows;
	}

	
	public List<ApiInfo> queryApiList(String typeId,String queryStr)
	{
		String sql = "SELECT id as apiId,api.api_path as apiPath ,app_name as appName,description,path,summary,swagger_app_id as swaggerAppId,tags,method,typeName  " +
                " FROM t_swagger_application_apis  api  " +
				" LEFT JOIN  (select a.api_path,group_concat(b.type_name separator ',') as typeName from type_apis a left join api_type b on a.type_id=b.id group by a.api_path) apiType ON api.api_path=apiType.api_path"+
                " WHERE 1=1  ";
		
		if(StringUtils.isNotEmpty(typeId)){
			sql = sql + " AND NOT EXISTS (SELECT 1 FROM type_apis where api_path=api.api_path and type_id= ? ) ";
		}
		
		if(StringUtils.isNotEmpty(queryStr)){
			sql = sql + " AND (path like ? or app_name like ? or description like ? or summary like ? or tags like ? or method like ? or typeName like ? ) ";
		}
		Query query = em.createNativeQuery(sql);
		
		if(StringUtils.isNotEmpty(typeId)){
			query.setParameter(1, typeId);
		}
		
		
		if(StringUtils.isNotEmpty(queryStr)){
			if(StringUtils.isNotEmpty(typeId)){
				query.setParameter(2, "%"+queryStr+"%");
				query.setParameter(3, "%"+queryStr+"%");
				query.setParameter(4, "%"+queryStr+"%");
				query.setParameter(5, "%"+queryStr+"%");
				query.setParameter(6, "%"+queryStr+"%");
				query.setParameter(7, "%"+queryStr+"%");
				query.setParameter(8, "%"+queryStr+"%");
			}else{
				query.setParameter(1, "%"+queryStr+"%");
				query.setParameter(2, "%"+queryStr+"%");
				query.setParameter(3, "%"+queryStr+"%");
				query.setParameter(4, "%"+queryStr+"%");
				query.setParameter(5, "%"+queryStr+"%");
				query.setParameter(6, "%"+queryStr+"%");
				query.setParameter(7, "%"+queryStr+"%");
			}

		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean( ApiInfo.class));
	
		List<ApiInfo> rows =(List<ApiInfo>)query.getResultList();
		return rows;
	}
	

}
