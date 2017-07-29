package com.rayfay.bizcloud.platforms.apigateway.swagger.repository;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by stzhang on 2017/3/2.
 */
@Repository
public interface SwaggerApplicationRepository extends JpaRepository<SwaggerApplication, String> {



}
