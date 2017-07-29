package com.rayfay.bizcloud.platforms.apigateway.swagger.controller;

import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfo;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.core.commons.message.UpdateClientInfoChannelListener;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiAccreditApp;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.AppApiTypeInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.message.SystemErrorCodeType;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ApiAccreditAppService;
import com.rayfay.bizcloud.platforms.apigateway.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangwei on 2017/7/12.
 */
@RestController
@RequestMapping(value = "/accredit")
@EnableBinding(UpdateClientInfoChannelListener.class)
public class AppAccreditController {

	private Logger logger = LoggerFactory.getLogger(AppAccreditController.class);
	private final ApiAccreditAppService apiAccreditAppService;

	@Autowired
	public AppAccreditController(ApiAccreditAppService apiAccreditAppService) {
		this.apiAccreditAppService = apiAccreditAppService;
	}

	@RequestMapping(value = "/apiTypeToApp", method = RequestMethod.POST)
	public Object saveApiToApp(@RequestBody Map data) {
		try {
			if(null != data && null != data.get("dummyAppId")) {
				// 先删除指定API的所有权限数据
				String appId = (String)data.get("dummyAppId");
				apiAccreditAppService.deleteByDummyAppId(appId);
				List<ApiAccreditApp> dataList = new ArrayList<>();
				if(null != data.get("apiTypes"))
				{
					for (String apiType : (List<String>)data.get("apiTypes")) {
						ApiAccreditApp apiAccreditApp = new ApiAccreditApp();
						apiAccreditApp.setApiTypeId(apiType);
						apiAccreditApp.setClientId((String)data.get("clientId"));
						apiAccreditApp.setClientKey((String)data.get("clientKey"));
						apiAccreditApp.setDummyAppId((String)data.get("dummyAppId"));
						apiAccreditApp.setOrganizationId((String)data.get("organizationId"));
						apiAccreditApp.setSpaceId((String)data.get("spaceId"));
						dataList.add(apiAccreditApp);
					}
				}
				if(dataList.size() > 0) {
					apiAccreditAppService.save(dataList);
				}

			}
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "接口授权");
		}
		return ResponseUtil.makeSuccessResponse();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object getApiAccreditApps(@RequestParam(name = "dummyAppId", required = false) String dummyAppId,
			@RequestParam(name = "apiTypeId", required = false) String apiTypeId) {
		try {
			List<ApiAccreditApp> dataList = apiAccreditAppService.query(dummyAppId, apiTypeId);
			return ResponseUtil.makeSuccessResponse(dataList);
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获得接口授权信息");
		}
	}

	@RequestMapping(value = "/apiType", method = RequestMethod.GET)
	public Object getApiAccreditApps(@RequestParam(name = "dummyAppId") String dummyAppId) {
		try {
			List<AppApiTypeInfo> dataList = apiAccreditAppService.queryApiTypeByApp(dummyAppId);
			return ResponseUtil.makeSuccessResponse(dataList);
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "获得接口授权信息");
		}
	}

	@RequestMapping(value = "/app", method = RequestMethod.DELETE)
	public Object delByClientId(@RequestParam String dummyAppId) {
		try {
			if (StringUtils.isNotEmpty(dummyAppId)) {
				// List<ApiAccreditApp> dataList = apiAccreditAppService.query(dummyAppId,null);
				apiAccreditAppService.deleteByDummyAppId(dummyAppId);
				// // 删除缓存
				// for(ApiAccreditApp data :dataList) {
				// apiAccreditAppService.delCache(data.getClientId(),data.getApiPath());
				// }
			}
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "解除接口授权");
		}
		return ResponseUtil.makeSuccessResponse();
	}

	@RequestMapping(value = "/space", method = RequestMethod.DELETE)
	public Object delBySpaceId(@RequestParam String spaceId) {
		try {
			if (StringUtils.isNotEmpty(spaceId)) {
				apiAccreditAppService.deleteBySpaceId(spaceId);
			}
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "解除接口授权");
		}
		return ResponseUtil.makeSuccessResponse();
	}

	@RequestMapping(value = "/organization", method = RequestMethod.DELETE)
	public Object delByOrganizationId(@RequestParam String organizationId) {
		try {
			if (StringUtils.isNotEmpty(organizationId)) {
				apiAccreditAppService.deleteByOrganizationId(organizationId);
			}
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "解除接口授权");
		}
		return ResponseUtil.makeSuccessResponse();
	}

	@RequestMapping(value = "/app", method = RequestMethod.PUT)
	public Object updateClientKey(@RequestParam String clientId, @RequestParam String clientKey) {
		try {
			if (StringUtils.isNotEmpty(clientId)) {
				apiAccreditAppService.updateClientKey(clientId, clientKey);
			}
		} catch (NRAPException e) {
			throw e;
		} catch (Exception e) {
			throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED, "解除接口授权");
		}
		return ResponseUtil.makeSuccessResponse();
	}

	@StreamListener(UpdateClientInfoChannelListener.UPDATE_CLIENT_INFO_APIGATEWAY_LISTENER)
	public void updateClientInfo(ClientInfo clientInfoMessage) {
		logger.info(String.format("更新ClientId【%s】的ClientKey为【%s】", clientInfoMessage.getClientId(),
				clientInfoMessage.getClientKey()));
		apiAccreditAppService.updateClientKey(clientInfoMessage.getClientId(), clientInfoMessage.getClientKey());
	}
}
