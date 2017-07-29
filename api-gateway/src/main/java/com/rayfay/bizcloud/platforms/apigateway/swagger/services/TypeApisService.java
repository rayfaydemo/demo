package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.TypeApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.ITypeApisRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.BizCloudSorter;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.PageRequestMakerUtil;

/**
 * Created by shenfu on 2017/3/31.
 */
@Service("TypeApisService")
public class TypeApisService {

	private final ITypeApisRepository typeApisRepository;

	@Autowired
	public TypeApisService(ITypeApisRepository typeApisRepository) {
		this.typeApisRepository = typeApisRepository;
	}


	public TypeApis insertTypeApis(TypeApis typeApis) {
		return typeApisRepository.save(typeApis);

	}

	public void delTypeApis(TypeApis typeApis){
		typeApisRepository.delete(typeApis);
	}
	
	
	public void delTypeApis(List<TypeApis>  typeApisList){
		typeApisRepository.delete(typeApisList);
	}
	
	
	public TypeApis findTypeApis(String typeId, String apiPath) {
		TypeApis appUser = typeApisRepository.findOne(new Specification<TypeApis>() {
			@Override
			public Predicate toPredicate(Root<TypeApis> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("typeId"), typeId));
				predicates.add(criteriaBuilder.equal(root.get("apiPath"), apiPath));
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		return appUser;
	}

	/**
	 * 根据查询条件查询接口分类
	 * 
	 * @param typeName
	 * @return 接口分类集合
	 */
	public List<TypeApis> queryTypeApis(String typeId) {

		List<TypeApis> list = typeApisRepository.findAll(new Specification<TypeApis>() {
			@Override
			public Predicate toPredicate(Root<TypeApis> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (StringUtils.isNotEmpty(typeId)) {
					predicates.add(criteriaBuilder.equal(root.get("typeId"),typeId));
				}
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		return list;
	}



	/**
	 * 根据查询条件查询人员身份信息(分页)
	 * @param pageSize 每页数据数
	 * @param pageNumber 当前页数
	 * @param queryMap 查询条件集合
	 * @return 人员身份信息集合
	 */
	public Page<TypeApis> queryTypeApisPageable(int pageSize, int pageNumber,String typeId,List<BizCloudSorter> sorter) {
			
		PageRequest pageRequest = PageRequestMakerUtil.makePageRequest(pageNumber, pageSize, sorter);
		Page<TypeApis> page = typeApisRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotEmpty(typeId)) {
				predicates.add(criteriaBuilder.equal(root.get("typeId"),typeId));
			}

			return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		}, pageRequest);
		return page;	
	}


}
