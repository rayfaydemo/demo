package com.rayfay.bizcloud.platforms.apigateway.utils;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenfu on 2017/4/10.
 */
public class ResponseUtil {

    /**
     * 分页结果
     * @param rowsTotal 总件数
     * @param rows 结果列表
     * @param <T> 继承了Collection的对象
     * @return 返回结果
     */
	public static <T extends Collection> Map<String, Object> makeSuccessResponse(long rowsTotal, T rows) {
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("success", true);
		responseMap.put("rowsTotal", rowsTotal);
		responseMap.put("rows", rows);

		return responseMap;
	}

    /**
     * 不分页结果
     * @param rows 结果列表
     * @param <T> 继承了Collection的对象
     * @return 返回结果
     */
	public static <T extends Collection> Map<String, Object> makeSuccessResponse(T rows) {
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("success", true);
		responseMap.put("rowsTotal", rows != null ? rows.size() : 0);
		responseMap.put("rows", rows);

		return responseMap;
	}
	
	
	public static Map<String, Object> makeSuccessResponse(Map<String, Object> row) {
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("success", true);
		responseMap.put("row", row);

		return responseMap;
	}
	

	public static Map<String, Object> makeSuccessResponse() {
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("success", true);

		return responseMap;
	}

    /**
     * 包含错误信息的结果
     * @param errorCode 错误代码
     * @param errorMessage 错误内容
     * @return 错误信息结果
     */
	public static Map<String, Object> makeErrorResponse(String errorCode, String errorMessage) {
		Map<String, Object> responseMap = new HashMap<>();

		responseMap.put("success", false);
		responseMap.put("errorCode", errorCode);
		responseMap.put("message", errorMessage);

		return responseMap;
	}
	
	 public static Map convertBean(Object bean)
	            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
	        Class type = bean.getClass();
	        Map returnMap = new HashMap();
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);

	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for (int i = 0; i< propertyDescriptors.length; i++) {
	            PropertyDescriptor descriptor = propertyDescriptors[i];
	            String propertyName = descriptor.getName();
	            if (!propertyName.equals("class")) {
	                Method readMethod = descriptor.getReadMethod();
	                Object result = readMethod.invoke(bean, new Object[0]);
	                if (result != null) {
	                    returnMap.put(propertyName, result);
	                } else {
	                    returnMap.put(propertyName, "");
	                }
	            }
	        }
	        return returnMap;
	    }
}
