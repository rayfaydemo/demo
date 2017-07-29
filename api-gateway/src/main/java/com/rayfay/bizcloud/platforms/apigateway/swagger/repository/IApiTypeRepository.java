package com.rayfay.bizcloud.platforms.apigateway.swagger.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiType;

/**
 * Created by shenfu on 2017/3/31.
 */
public interface IApiTypeRepository extends PagingAndSortingRepository<ApiType, String>,JpaSpecificationExecutor<ApiType> {
}
