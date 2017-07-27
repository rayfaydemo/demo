package com.example.message;

import com.rayfay.bizcloud.core.commons.exception.ErrorCode;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeDefinition;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeValuedEnum;

/**
 * Created by shenfu on 2017/4/14.
 */
@ErrorCodeDefinition(thousands = 4)
public class SystemErrorCodeType {
    @ErrorCode(msg = "获取数据失败！")
    public static ErrorCodeValuedEnum E_GET_DATA_FALED;

    @ErrorCode(code = 2, msg = "{0}失败！")
    public static ErrorCodeValuedEnum E_ACTION_FALED;

    @ErrorCode(code = 3, msg = "参数错误：{0}！")
    public static ErrorCodeValuedEnum E_PARAM_ERR;

    @ErrorCode(code = 4, msg = "权限不足，请联系管理员！")
    public static ErrorCodeValuedEnum E_PERMISSION_DENY_ERR;
}
