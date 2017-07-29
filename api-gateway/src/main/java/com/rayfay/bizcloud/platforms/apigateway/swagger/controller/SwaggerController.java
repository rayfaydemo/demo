package com.rayfay.bizcloud.platforms.apigateway.swagger.controller;

import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SearchApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ApiAccreditAppService;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ISwaggerParserService;
import com.rayfay.bizcloud.platforms.apigateway.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stzhang on 2017/3/8.
 */
@RestController
@RequestMapping("/swagger")
public class SwaggerController {
    @Autowired
    private ISwaggerParserService swaggerParserService;

    @Autowired
    private ApiAccreditAppService apiAccreditAppService;

    @RequestMapping(value = "/syn",method = {RequestMethod.GET})
    public void syn(@RequestParam String appName){
        swaggerParserService.synSwaggerDataFromAppServer(appName);
    }

    @RequestMapping(value = "/api/docs",method = {RequestMethod.GET})
    public Object getApiDocs(@RequestParam String pathId, HttpServletRequest request){
        SBaseRequest sBaseRequest = SBaseRequest.parse(request);
        String docs = swaggerParserService.queryApiDocs(pathId, sBaseRequest);
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> apiDocs = new HashMap<>();
        apiDocs.put("apiDocs", docs);
        rows.add(apiDocs);
        return ResponseUtil.makeSuccessResponse(rows);
    }

    @RequestMapping(value = "/api/search",method = {RequestMethod.POST})
    @ResponseBody
    public List<SearchApis> searchSwaggerApis(String text){
        return  swaggerParserService.searchSwaggerApis(text);
    }

    @RequestMapping(value = "/api",method = {RequestMethod.GET})
    public Object searchApis(
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "queryStr") String queryStr){
        Map<String, Object> responseMap;
        List<ApiInfo> apis = swaggerParserService.searchSwaggerApisPageable(pageSize,pageNumber,queryStr);
        long rowNum=apiAccreditAppService.queryAppCnt(queryStr);
		 List<Map<String, Object>> rows = new ArrayList<>();
			for (ApiInfo one : apis) {
				Map<String, Object> log = toApiInfoMap(one);
				rows.add(log);
			}
        responseMap = ResponseUtil.makeSuccessResponse(rowNum, rows);
        return responseMap;
    }
    
	/**
	 * 将接口对象转为MAP
	 * @param apiInfo 接口对象信息
	 * @return 接口对象信息map
	 */
    private Map<String, Object> toApiInfoMap(ApiInfo apiInfo){
    	
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("apiId", apiInfo.getApiId());
    	returnMap.put("swaggerAppId", apiInfo.getSwaggerAppId());
    	returnMap.put("apiPath", apiInfo.getApiPath());
    	returnMap.put("appName", apiInfo.getAppName());
    	returnMap.put("tags", apiInfo.getTags());
    	returnMap.put("path", apiInfo.getPath());
    	returnMap.put("summary", apiInfo.getSummary());
     	returnMap.put("description", apiInfo.getDescription());
     	returnMap.put("method", apiInfo.getMethod());
     	returnMap.put("typeName", apiInfo.getTypeName());
     	returnMap.put("dummyAppIds", apiInfo.getDummyAppId());
    	return returnMap;
    }
    

}
