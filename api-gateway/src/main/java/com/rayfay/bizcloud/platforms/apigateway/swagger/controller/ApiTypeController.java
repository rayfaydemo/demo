package com.rayfay.bizcloud.platforms.apigateway.swagger.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.TypeApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.TypeApisInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.message.ApiErrorCodeType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.message.SystemErrorCodeType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ApiTypeService;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.TypeApisService;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.BizCloudSorter;
import com.rayfay.bizcloud.platforms.apigateway.swagger.utils.ResponseUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by shenfu on 2017/3/31.
 */
@RestController
@RequestMapping(value = "/apitype")
public class ApiTypeController {

	private ApiTypeService apiTypeService;
	
	private TypeApisService typeApisService;
	
	
	@Autowired
	public ApiTypeController(ApiTypeService apiTypeService,TypeApisService typeApisService) {
		this.apiTypeService = apiTypeService;
		this.typeApisService = typeApisService;
	}
	
	
	@ApiOperation(value = "插入接口分类", notes = "插入接口分类")
	@RequestMapping(value = "/insertType", method = RequestMethod.POST)
	public Object insertType(@RequestBody ApiType typeInfo){
		Map<String, Object> responseMap;
		try {
		boolean exists =apiTypeService.exists(typeInfo.getTypeName(), typeInfo.getId());
		if(exists){
			throw new NRAPException(ApiErrorCodeType.E_SERVICE_TYPE_EXISTS, typeInfo.getTypeName());
		}else{
			typeInfo=apiTypeService.insertApiType(typeInfo);
		    Map<String, Object> apiTypeMap =toTypeMap(typeInfo);
		    responseMap=ResponseUtil.makeSuccessResponse(apiTypeMap);
		
	    	}
			} catch (NRAPException e) {
				throw e;
			} catch (Exception e) {
				throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "插入接口分类");
			}
	

		return responseMap;
	}
	
	@ApiOperation(value="删除接口分类", notes="删除接口分类")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "接口分类ID", required = true, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/delType", method = RequestMethod.GET)
	public Object delType(@RequestParam String typeId){
		Map<String, Object> responseMap;
		try{
			//删除接口分类下分类接口关系
			List<TypeApis> typeApisList=typeApisService.queryTypeApis(typeId);
			typeApisService.delTypeApis(typeApisList);			
			//删除接口分类
			apiTypeService.delApiType(typeId);
		    responseMap = ResponseUtil.makeSuccessResponse();
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除接口分类");
		}	
		return responseMap;
	}
	
	
	
	@ApiOperation(value = "插入分类接口关系", notes = "插入分类接口关系")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "接口分类ID", required = true, paramType = "query", dataType = "String"),
	        @ApiImplicitParam(name = "apiPaths", value = "接口ID(多个)", required = true, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/insertTypeApis", method = RequestMethod.POST)
	public Object insertTypeApis(@RequestParam String typeId,@RequestParam String apiPaths){
		Map<String, Object> responseMap;
		String[] apiPath=apiPaths.split(",");
		
		try {
			List<Map<String, Object>> rows = new ArrayList<>();
			for(int i=0;i<apiPath.length;i++){
				TypeApis typeApis=new TypeApis();
				typeApis.setTypeId(typeId);
				typeApis.setApiPath(apiPath[i]);
		        typeApisService.insertTypeApis(typeApis);
				Map<String, Object> userMap =toApisMap(typeApis);
				rows.add(userMap);
			}
			responseMap=ResponseUtil.makeSuccessResponse(rows.size(),rows);
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "创建人员");
		}
		return responseMap;
	}
	
	
	
	@ApiOperation(value="移除分类接口关系", notes="移除分类接口关系")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "接口分类ID", required = true, paramType = "query", dataType = "String"),
	        @ApiImplicitParam(name = "apiPath", value = "服务接口ID)", required = true, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/deleteTypeApis", method = RequestMethod.GET)
	public Object deleteTypeApis(@RequestParam String typeId,@RequestParam String apiPath){
		Map<String, Object> responseMap;
		try{
			TypeApis typeApis=typeApisService.findTypeApis(typeId, apiPath);
			typeApisService.delTypeApis(typeApis);
		    responseMap = ResponseUtil.makeSuccessResponse();
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "删除人员");
		}
		
		return responseMap;
	}
	
   
	@ApiOperation(value="获取接口分类(分页)", notes="获取接口分类")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "pageSize", value = "显示行数", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "pageNumber", value = "页码", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "typeName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "sorter", value = "排序", paramType = "query", dataType = "jsonArray") })
	@RequestMapping(value = "/findApiTypePage", method = RequestMethod.POST)
	public Object findApiTypePage(
			@RequestParam (name = "pageSize", defaultValue = "20") int pageSize,
			@RequestParam (name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "typeName",required = false) String typeName,
			@RequestBody(required = false) List<BizCloudSorter> sorter
			){
		Map<String, Object> responseMap;
		try{
			Page<ApiType> result = apiTypeService.findApiTypePageable(pageSize, pageNumber, typeName, sorter);
			
			 List<Map<String, Object>> rows = new ArrayList<>();
				for (ApiType one : result) {
					Map<String, Object> log = toTypeMap(one);
					rows.add(log);
				}
				responseMap=ResponseUtil.makeSuccessResponse(result.getTotalElements(),rows);
			
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
		}
	
		return responseMap;
	}
	
	
	@ApiOperation(value="获取接口分类信息", notes="获取接口分类信息")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "分类ID", required = true, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/getApiType", method = RequestMethod.GET)
	public Object getApiType(
			@RequestParam(name="typeId",required=true)  String typeId
			){
		Map<String, Object> responseMap;
		try {

		  ApiType apiType=apiTypeService.findOne(typeId);
		  Map<String, Object> typeMap =toTypeMap(apiType);
	 	  responseMap=ResponseUtil.makeSuccessResponse(typeMap);

		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
		}
			return responseMap;
	}
	
	@ApiOperation(value="获取接口分类详细信息", notes="获取接口分类详细信息")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "分类ID", required = true, paramType = "query", dataType = "String"),
	        @ApiImplicitParam(name = "queryStr", value = "检索文本", required = false, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/getApiTypeDetail", method = RequestMethod.GET)
	public Object getApiTypeDetail(
			@RequestParam(name="typeId",required=true)  String typeId,@RequestParam(name="queryStr",required=false)  String queryStr
			){
		Map<String, Object> responseMap;
		try {

			List<TypeApisInfo> result = apiTypeService.getTypeDetailList(typeId,queryStr);
			 List<Map<String, Object>> rows = new ArrayList<>();
				for (TypeApisInfo one : result) {
					Map<String, Object> log = toApiDetailMap(one);
					rows.add(log);
				}
				responseMap=ResponseUtil.makeSuccessResponse(result.size(),rows);

		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
		}
			return responseMap;
	}
	
	
	@ApiOperation(value="获取分类可添加的接口", notes="获取分类可添加的接口")
	@ApiImplicitParams(value = {
	        @ApiImplicitParam(name = "typeId", value = "分类ID", required = false, paramType = "query", dataType = "String"),
	        @ApiImplicitParam(name = "queryStr", value = "检索文本", required = false, paramType = "query", dataType = "String")
	})
	@RequestMapping(value = "/queryApiList", method = RequestMethod.GET)
	public Object queryApiList(
			@RequestParam(name="typeId",required=false)  String typeId,@RequestParam(name="queryStr",required=false)  String queryStr
			){
		Map<String, Object> responseMap;
		try {

			List<ApiInfo> result = apiTypeService.queryApiList(typeId, queryStr);
			 List<Map<String, Object>> rows = new ArrayList<>();
				for (ApiInfo one : result) {
					Map<String, Object> log = toApiInfoMap(one);
					rows.add(log);
				}
				responseMap=ResponseUtil.makeSuccessResponse(result.size(),rows);

		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获取");
		}
			return responseMap;
	}
	
	/**
	 * 将接口分类对象转为MAP
	 * @param apiType 接口分类对象信息
	 * @return 接口分类信息map
	 */
    private Map<String, Object> toTypeMap(ApiType apiType){
    	
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("typeId", apiType.getId());
    	returnMap.put("typeName", apiType.getTypeName());
    	returnMap.put("description", apiType.getDescription());
    	returnMap.put("serviceNum", apiTypeService.getTypeDetailList( apiType.getId(),"").size());
    	return returnMap;
    	
    	
    }
    
	/**
	 * 将分类接口关系对象转为MAP
	 * @param typeApis 分类接口关系对象信息
	 * @return 分类接口关系信息map
	 */
    private Map<String, Object> toApisMap(TypeApis typeApis){
    	
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("typeId", typeApis.getTypeId());
    	returnMap.put("apiPath", typeApis.getApiPath());
    	return returnMap;
    	
    	
    }
    
    
	/**
	 * 将分类接口关系对象转为MAP
	 * @param typeApis 分类接口关系对象信息
	 * @return 分类接口关系信息map
	 */
    private Map<String, Object> toApiDetailMap(TypeApisInfo typeApisInfo){
    	
    	Map<String, Object> returnMap = new HashMap<String, Object>();
    	returnMap.put("typeId", typeApisInfo.getTypeId());
    	returnMap.put("apiId", typeApisInfo.getApiId());
    	returnMap.put("swaggerAppId", typeApisInfo.getSwaggerAppId());
    	returnMap.put("apiPath", typeApisInfo.getApiPath());
    	returnMap.put("appName", typeApisInfo.getAppName());
    	returnMap.put("tags", typeApisInfo.getTags());
    	returnMap.put("path", typeApisInfo.getPath());
    	returnMap.put("summary", typeApisInfo.getSummary());
     	returnMap.put("description", typeApisInfo.getDescription());
     	returnMap.put("method", typeApisInfo.getMethod());
    	return returnMap;
    }
    
	/**
	 * 将分类接口关系对象转为MAP
	 * @param typeApis 分类接口关系对象信息
	 * @return 分类接口关系信息map
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
    	return returnMap;
    }
    
}
