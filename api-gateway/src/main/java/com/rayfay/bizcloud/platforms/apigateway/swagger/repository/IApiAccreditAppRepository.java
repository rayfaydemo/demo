package com.rayfay.bizcloud.platforms.apigateway.swagger.repository;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiAccreditApp;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SearchApis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/7/12.
 */
@Repository
public interface IApiAccreditAppRepository extends JpaSpecificationExecutor<ApiAccreditApp>, JpaRepository<ApiAccreditApp, Long> {

    @Transactional
    @Modifying
    @Query("delete from ApiAccreditApp u where u.apiTypeId = :apiTypeId")
    public void deleteByApiTypeId(@Param("apiTypeId") String apiTypeId);

    @Transactional
    @Modifying
    @Query("delete from ApiAccreditApp u where u.dummyAppId = :dummyAppId")
    public void deleteByDummyAppId(@Param("dummyAppId") String dummyAppId);

    @Transactional
    @Modifying
    @Query("delete from ApiAccreditApp u where u.spaceId = :spaceId")
    public void deleteBySpaceId(@Param("spaceId") String spaceId);

    @Transactional
    @Modifying
    @Query("delete from ApiAccreditApp u where u.organizationId = :organizationId")
    public void deleteByOrganizationId(@Param("organizationId") String organizationId);

    @Transactional
    @Modifying
    @Query("update ApiAccreditApp u set u.clientKey = :clientKey where u.clientId = :clientId")
    public void updateClientKey(@Param("clientId") String clientId,@Param("clientKey") String clientKey);

//    @Transactional
//    @Modifying
//    @Query(value = "delete  from t_api_accredit_app where not exists (select 1 from t_swagger_application_apis u where u.api_path = t_api_accredit_app.api_path )",nativeQuery = true)
//    public void clearNotExistData();

    @Query(" from ApiAccreditApp u where u.apiTypeId = :apiTypeId and u.clientId = :clientId")
    public ApiAccreditApp query(@Param("clientId") String clientId,@Param("apiTypeId") String apiTypeId);

    @Query(value ="select a.client_key from t_api_accredit_app a, type_apis  b where a.api_type_id=b.type_id and b.api_path=:apiPath and a.client_id=:clientId ",nativeQuery = true)
    public List queryClientKey(@Param("clientId") String clientId,@Param("apiPath") String apiPath);

//    @Query(value = "select c.api_path, count(1) as appCnt from(select distinct b.api_path,a.client_id from t_api_accredit_app a,type_apis b  where a.api_type_id=b.type_id and b.api_path in :apiPathList) c group by c.api_path " ,nativeQuery = true)
//    public List queryAppCnt(@Param("apiPathList") List<String> apiPathList);

    @Query(value = "select count(1) as appCnt from t_swagger_application_apis where  path like :queryStr or app_name like :queryStr or description like :queryStr or summary like :queryStr or tags like :queryStr or method like :queryStr " ,nativeQuery = true)
    public List queryAppCnt(@Param("queryStr") String queryStr);

    @Query(value = "select a.dummy_app_id,b.id,b.type_name,b.description  from t_api_accredit_app a, api_type b  where a.api_type_id=b.id and a.dummy_app_id=:dummyAppId " ,nativeQuery = true)
    public List queryApiTypeByApp(@Param("dummyAppId") String dummyAppId);

}
