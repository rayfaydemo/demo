package com.rayfay.bizcloud.platforms.apigateway.swagger.message;

import com.rayfay.bizcloud.core.commons.exception.ErrorCode;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeDefinition;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeValuedEnum;

/**
 * Created by shenfu on 2017/4/13.
 */
@ErrorCodeDefinition(thousands = 71)
public class ApiErrorCodeType {
	@ErrorCode(code = 2,msg = "该接口分类({0})已存在！")
	public static ErrorCodeValuedEnum E_SERVICE_TYPE_EXISTS;


}
