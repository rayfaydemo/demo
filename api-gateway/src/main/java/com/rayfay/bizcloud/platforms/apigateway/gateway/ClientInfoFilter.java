package com.rayfay.bizcloud.platforms.apigateway.gateway;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfo;
import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfoKeys;
import com.rayfay.bizcloud.core.commons.utils.EncryptorUtils;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ApiAccreditAppService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shenfu on 2017/7/13.
 */
public class ClientInfoFilter extends ZuulFilter {
	private final ApiAccreditAppService apiAccreditAppService;

	public ClientInfoFilter(ApiAccreditAppService apiAccreditAppService) {
		this.apiAccreditAppService = apiAccreditAppService;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String clientInfo = EncryptorUtils.decrypt(request.getHeader(ClientInfoKeys.CLIENT_INFO));
		ClientInfo clientInfoObj = JSONObject.parseObject(clientInfo, ClientInfo.class);
		if (clientInfoObj != null) {
			String clientId = clientInfoObj.getClientId();
			String clientKey = clientInfoObj.getClientKey();
			String path = cleanPath(request);

			// TODO 超级客户端信息需要添加到数据库中
			if (isSuperClient(clientId, clientKey)) {
				ctx.setSendZuulResponse(true);// 对该请求进行路由
				ctx.setResponseStatusCode(200);
				ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
			} else {
				String keyFromDb = apiAccreditAppService.queryClientKey(clientId, path);

				if (StringUtils.isBlank(keyFromDb) || !StringUtils.equalsIgnoreCase(clientKey, keyFromDb)) {
					ctx.getResponse().setContentType("text/html;charset=UTF-8");
					ctx.setSendZuulResponse(false);
					ctx.setResponseStatusCode(401);
					ctx.setResponseBody("目标接口未对本应用授权，请联系管理员！");// 返回错误内容
					ctx.set("isSuccess", false);
				} else {
					ctx.setSendZuulResponse(true);// 对该请求进行路由
					ctx.setResponseStatusCode(200);
					ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
				}
			}
		} else {
			ctx.setSendZuulResponse(true);// 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
		}


		return null;
	}

	private String cleanPath(HttpServletRequest request) {
		String result = StringUtils.replaceOnce(request.getServletPath(), "/hosts", "/");
		result = StringUtils.join(result, request.getMethod());
		return result;
	}

	private boolean isSuperClient(String clientId, String clientKey) {
		return StringUtils.equalsIgnoreCase(clientId, "hiauwfhfi2398")
				&& StringUtils.equalsIgnoreCase(clientKey, "dfna8302n;fe;g-4208[qf");
	}
}
