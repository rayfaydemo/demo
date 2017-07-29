package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiAccreditApp;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.AppApiTypeInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SearchApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.IApiAccreditAppRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/7/12.
 */
@Service("ApiAccreditAppService")
public class ApiAccreditAppService {

    private final IApiAccreditAppRepository iApiAccreditAppRepository;

    @Autowired
    public ApiAccreditAppService(IApiAccreditAppRepository iAppAccreditApisRepository) {
        this.iApiAccreditAppRepository = iAppAccreditApisRepository;
    }

    /**
     * @param dataList 数据列表
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void save(final List<ApiAccreditApp> dataList) {
        iApiAccreditAppRepository.save(dataList);
    }

    /**
     * @param apiTypeId apiTypeId
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void deleteByApiTypeId(String apiTypeId) {
        iApiAccreditAppRepository.deleteByApiTypeId(apiTypeId);
    }

    /**
     * @param spaceId spaceId
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void deleteBySpaceId(String spaceId) {
        iApiAccreditAppRepository.deleteBySpaceId(spaceId);
    }

    /**
     * @param organizationId organizationId
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void deleteByOrganizationId(String organizationId) {
        iApiAccreditAppRepository.deleteByOrganizationId(organizationId);
    }

    /**
     * @param clientId clientId
     * @param clientKey clientKey
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void updateClientKey(String clientId,String clientKey) {
        iApiAccreditAppRepository.updateClientKey(clientId,clientKey);
    }

    /**
     * @param dummyAppId dummyAppId
     */
    @CacheEvict(value="accreditCache",allEntries=true)
    public void deleteByDummyAppId(String dummyAppId) {
        iApiAccreditAppRepository.deleteByDummyAppId(dummyAppId);
    }


    public List<ApiAccreditApp> query(String dummyAppId, String apiTypeId) {
        List<ApiAccreditApp> list = iApiAccreditAppRepository.findAll(new Specification<ApiAccreditApp>() {
            @Override
            public Predicate toPredicate(Root<ApiAccreditApp> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(StringUtils.isNotEmpty(dummyAppId)) {
                    predicates.add(criteriaBuilder.equal(root.get("dummyAppId"), dummyAppId));
                }
                if(StringUtils.isNotEmpty(apiTypeId)) {
                    predicates.add(criteriaBuilder.equal(root.get("apiTypeId"), apiTypeId));
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        });
        return list;
    }

    public List<AppApiTypeInfo> queryApiTypeByApp(String dummyAppId) {
        List<AppApiTypeInfo> resultList = new ArrayList<>();
        List dataList = iApiAccreditAppRepository.queryApiTypeByApp(dummyAppId);
        if(null != dataList) {
            for(Object data: dataList)
            {
                AppApiTypeInfo info = new AppApiTypeInfo();
                info.setDummyAppId((String)(((Object[])data)[0]));
                info.setApiTypeId((String)(((Object[])data)[1]));
                info.setApiTypeName((String)(((Object[])data)[2]));
                info.setApiTypeDesc((String)(((Object[])data)[3]));
                resultList.add(info);
            }
        }
        return resultList;
    }

    public long queryAppCnt(String queryStr) {
        Map<String,Integer> dataMap = new HashMap<>();
        List dataList = iApiAccreditAppRepository.queryAppCnt("%"+queryStr+"%");
        Object data=dataList.get(0);
        long val = Long.parseLong(String.valueOf(data));
        return val;
    }


    @Cacheable(value="accreditCache",key="#clientId + #apiPath")
    public String queryClientKey(String clientId, String apiPath) {
        List dataList = iApiAccreditAppRepository.queryClientKey(clientId,apiPath);
        if(null != dataList && dataList.size() == 1) {
            return (String)dataList.get(0);
        }
        else
        {
            return null;
        }
    }


//    @CachePut(value="accreditCache",key="#data.clientId + #data.apiPath")
//    public String putCache(ApiAccreditApp data)
//    {
//        return data.getClientKey();
//    }
//
//    @CacheEvict(value="accreditCache",key="#clientId + #apiPath")
//    public void delCache(String clientId, String apiPath)
//    {
//    }
}
