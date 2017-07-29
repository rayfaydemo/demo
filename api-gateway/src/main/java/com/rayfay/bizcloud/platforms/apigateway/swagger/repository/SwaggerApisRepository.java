package com.rayfay.bizcloud.platforms.apigateway.swagger.repository;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplicationApis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by stzhang on 2017/3/2.
 */
@Repository
public interface SwaggerApisRepository extends JpaRepository<SwaggerApplicationApis, String> {

    @Query(" from SwaggerApplicationApis u where u.apiPath = :apiPath")
    public SwaggerApplicationApis getApiByApiPath(@Param("apiPath") String apiPath);


    @Transactional
    @Modifying
    @Query("delete from SwaggerApplicationApis u where u.appName = :appName")
    public void deleteAllApiByApp(@Param("appName") String appName);


    @Query(" from SwaggerApplicationApis u where u.apiPath like :text or u.summary like :text or description like :text ")
    public List<SwaggerApplicationApis> searchApiByText(@Param("text") String text);

    @Query(" from SwaggerApplicationApis u where u.apiPath like :text or u.summary like :text or description like :text ")
    public Page<SwaggerApplicationApis> searchApiByTextPageable(@Param("text") String text, Pageable pageable);

}
