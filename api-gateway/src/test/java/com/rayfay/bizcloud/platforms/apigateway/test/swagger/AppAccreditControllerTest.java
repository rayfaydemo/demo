package com.rayfay.bizcloud.platforms.apigateway.test.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created by zhangwei on 2017/6/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
///@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("dev")
public class AppAccreditControllerTest {
    private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationConnect;
	
	@Before
	public void setUp() throws JsonProcessingException {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
	}

	@Test
	public void saveApiToApp() throws Exception {
		String requestBody = "{\"apiPath\":\"diamond/del\",\"clientId\":\"clientId1\", \"clientKey\":\"clientKey1\",\"spaceId\":\"12\",\"organizationId\":\"10240\"}";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/accredit/apiToApp").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).content(requestBody);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}


	@Test
	public void appToApi() throws Exception {
		String requestBody = "{\"apiPath\":\"diamond/add\",\"clientId\":\"clientId2\", \"clientKey\":\"clientKey2\",\"spaceId\":\"12\",\"organizationId\":\"10240\"}";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/accredit/apiToApp").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).content(requestBody);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}

	@Test
	public void getList() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		mvc.perform(MockMvcRequestBuilders.get("/accredit/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rowsTotal", is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows[0].apiPath", is("diamond/del")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rows[1].apiPath", is("diamond/add")));
	}


	@Test
	public void delApiAccreditApps() throws Exception {
		String requestBody = "[{\"apiPath\":\"diamond/add\",\"clientId\":\"clientId2\"}]";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/accredit/list").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).content(requestBody);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}

	@Test
	public void delByClientId() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("clientId", Collections.singletonList("clientId1"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/accredit/app").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).params(params);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}

	@Test
	public void delBySpaceId() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("spaceId", Collections.singletonList("spaceId1"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/accredit/space").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).params(params);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}

	@Test
	public void delByOrganizationId() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.put("organizationId", Collections.singletonList("organizationId1"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/accredit/organization").contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).params(params);
		mvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
	}

}