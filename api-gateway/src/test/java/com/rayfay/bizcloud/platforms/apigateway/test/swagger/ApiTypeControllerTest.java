package com.rayfay.bizcloud.platforms.apigateway.test.swagger;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.TypeApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.IApiTypeRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.ITypeApisRepository;

/**
 * Created by zhangwei on 2017/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
///@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("dev")
public class ApiTypeControllerTest {
    private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationConnect;
	
	@Autowired
    private IApiTypeRepository apiTypeRepository;
	
	@Autowired
    private ITypeApisRepository typeApisRepository;
	
	
	@Before
	public void setUp() throws JsonProcessingException {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
	}

	@Test
	public void insertType_success() throws Exception {
		String requestBody="{\"typeName\":\"测试分类UNIT\",\"description\":\"测试\"}";
    	ResultActions actions=mvc.perform(MockMvcRequestBuilders.post("/apitype/insertType").accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON).content(requestBody))
    	.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.row").isMap())
		.andExpect(MockMvcResultMatchers.jsonPath("$.row.typeId").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.row.typeName", is("测试分类UNIT")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.row.description", is("测试")));
    	
    	ApiType typeInfo = apiTypeRepository.findOne(
				(Root<ApiType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
					List<Predicate> predicates = new ArrayList<>();
					predicates.add(criteriaBuilder.equal(root.get("typeName"), "测试分类UNIT"));
					return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
				});
    	
    	
		Assert.assertTrue("数据获取成功", typeInfo != null);
		Assert.assertTrue("分类名称","测试分类UNIT".equals(typeInfo.getTypeName()));
		Assert.assertTrue("分类描述","测试".equals(typeInfo.getDescription()));
		
	}

	@Test
	public void delType_success() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d3eb49c015d3f3890dc0004"));
		mvc.perform(MockMvcRequestBuilders.get("/apitype/delType").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
		
    	ApiType typeInfo = apiTypeRepository.findOne("4028f8355d3eb49c015d3f3890dc0004");
		Assert.assertTrue("数据删除成功", typeInfo == null);
		
	}
	
	@Test
	public void insertTypeApis_success() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d3eb49c015d3f3890dc0004"));
		params.put("apiPaths", Collections.singletonList("//diamond-application-service/appuser/insertAppUser,//diamond-application-service/appuser/deleteAppUser"));
		ResultActions actions=mvc.perform(MockMvcRequestBuilders.post("/apitype/insertTypeApis").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows",hasSize(2)));
		
    	actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].typeId",is("4028f8355d3eb49c015d3f3890dc0004")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiPath", is("//diamond-application-service/appuser/insertAppUser")));
	
	    actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].typeId",is("4028f8355d3eb49c015d3f3890dc0004")))
		       .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiPath", is("//diamond-application-service/appuser/deleteAppUser")));	
		
	    TypeApis typeApis = typeApisRepository.findOne(
			(Root<TypeApis> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("typeId"), "4028f8355d3eb49c015d3f3890dc0004"));
				predicates.add(criteriaBuilder.equal(root.get("apiPath"), "//diamond-application-service/appuser/insertAppUser"));
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			});
	    Assert.assertTrue("数据获取成功", typeApis != null);
	
	    typeApis = typeApisRepository.findOne(
			(Root<TypeApis> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("typeId"), "4028f8355d3eb49c015d3f3890dc0004"));
				predicates.add(criteriaBuilder.equal(root.get("apiPath"), "//diamond-application-service/appuser/deleteAppUser"));
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			});
	    Assert.assertTrue("数据获取成功", typeApis != null);
		
	}
	
	@Test
	public void deleteTypeApis_success() throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d39df15015d39e138c40000"));
		params.put("apiPath", Collections.singletonList("//diamond-application-service/appuser/deleteAppUser"));
		mvc.perform(MockMvcRequestBuilders.get("/apitype/deleteTypeApis").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
		
	    TypeApis typeApis = typeApisRepository.findOne(
			(Root<TypeApis> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.equal(root.get("typeId"), "4028f8355d39df15015d39e138c40000"));
				predicates.add(criteriaBuilder.equal(root.get("apiPath"), "//diamond-application-service/appuser/deleteAppUser"));
				return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			});
		Assert.assertTrue("数据删除成功", typeApis == null);
	}
	
	@Test
	public void findApiTypePage_success() throws Exception {
		String requestBody="[]";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("pageSize", Collections.singletonList("20"));
		params.put("pageNumber", Collections.singletonList("0"));
		params.put("typeName", Collections.singletonList("测试9"));
		ResultActions actions=mvc.perform(MockMvcRequestBuilders.post("/apitype/findApiTypePage").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON).content(requestBody).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows",hasSize(2)));
		
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].typeId",is("4028f8355d3fb285015d3fb4bede0000")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].typeName", is("测试91")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].description", is("223")));

         actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].typeId",is("4028f8355d549374015d549ae12b0003")))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].typeName", is("测试9")))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].description", is("123")));
 
	}
	
	@Test
	public void getApiType_success() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d39df15015d39e138c40000"));
		ResultActions actions=mvc.perform(MockMvcRequestBuilders.get("/apitype/getApiType").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.row.typeId",is("4028f8355d39df15015d39e138c40000")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.row.typeName", is("测试12")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.row.description", is("242144444444444444444444444412")));
	}
	
	@Test
	public void getApiTypeDetail_success() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d3fb285015d3fb4bede0000"));
		params.put("queryStr", Collections.singletonList(""));

		ResultActions actions=mvc.perform(MockMvcRequestBuilders.get("/apitype/getApiTypeDetail").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows",hasSize(2)));
		
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].typeId",is("4028f8355d3fb285015d3fb4bede0000")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiId", is("4028f8355d348cad015d349de9920015")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].swaggerAppId", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiPath", is("//diamond-application-service/application/version")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].appName", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].tags", is("application-version-controller")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].path", is("/application/version")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].summary", is("获取应用版本信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].description", is("获取应用版本信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].method", is("GET")));

	     	
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].typeId",is("4028f8355d3fb285015d3fb4bede0000")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiId", is("4028f8355d348cad015d349de9930018")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].swaggerAppId", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiPath", is("//diamond-application-service/appuser/insertAppUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].appName", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].tags", is("application-user-controller")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].path", is("/appuser/insertAppUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].summary", is("插入管理员")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].description", is("插入管理员")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].method", is("POST")));
	}
	
	@Test
	public void queryApiList_success() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("typeId", Collections.singletonList("4028f8355d3fb285015d3fb4bede0000"));
		params.put("queryStr", Collections.singletonList("用户信息"));

		ResultActions actions=mvc.perform(MockMvcRequestBuilders.get("/apitype/queryApiList").contentType(MediaType.APPLICATION_JSON).
				 accept(MediaType.APPLICATION_JSON).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows",hasSize(3)));
		
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].typeName").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiId", is("4028f8355d348cad015d349de9930019")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].swaggerAppId", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiPath", is("//diamond-application-service/appuser/queryAppUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].appName", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].tags", is("application-user-controller")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].path", is("/appuser/queryAppUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].summary", is("获取应用用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].description", is("获取应用用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].method", is("GET")));

	     	
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].typeName").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiId", is("4028f8355d348cad015d349de997003c")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].swaggerAppId", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiPath", is("//diamond-application-service/orguser/queryOrgUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].appName", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].tags", is("organization-user-controller")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].path", is("/orguser/queryOrgUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].summary", is("获取组织用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].description", is("获取组织用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].method").isEmpty());
		 
		 actions.andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].typeName").isEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].apiId", is("4028f8355d348cad015d349de99a005e")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].swaggerAppId", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].apiPath", is("//diamond-application-service/spaceuser/querySpaceUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].appName", is("diamond-application-service")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].tags", is("space-user-controller")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].path", is("/spaceuser/querySpaceUser")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].summary", is("获取空间用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].description", is("获取空间用户信息")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.rows[2].method").isEmpty());
		 
	}
	

}