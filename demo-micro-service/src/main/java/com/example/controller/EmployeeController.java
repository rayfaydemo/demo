package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.EmployeeInfo;
import com.example.message.SystemErrorCodeType;
import com.example.service.EmployeeService;
import com.example.utils.ResponseUtil;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HP on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "获取人员信息", notes = "分页获取人员信息", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageSize", value = "显示行数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataType = "string")})
    @RequestMapping(value = "getPageable", method = RequestMethod.GET)
    public Object getEmployeesPageable(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "name", required = false) String name) {
        logger.info("paras:pageSize={},pageNumber={},name={}",pageSize,pageNumber,name);
        try {
            Page<EmployeeInfo> result = employeeService.findPageable(pageSize,pageNumber-1,name);
            return ResponseUtil.makeSuccessResponse(result.getTotalElements(), result.getContent());
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_GET_DATA_FALED);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @Transactional
    public Object addEmployee(@RequestBody EmployeeInfo data) {
        try {
            employeeService.save(data);
            int i=0;
            int a = 20/i;
            return  ResponseUtil.makeSuccessResponse();
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED,"增加");
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Object updateEmployee(@RequestBody EmployeeInfo data) {
        try {
            EmployeeInfo dbData = employeeService.findOne(data.getId());
            dbData.setName(data.getName());
            employeeService.save(dbData);
            return  ResponseUtil.makeSuccessResponse();
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED,"更新");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delEmployee(@RequestParam long id) {
        try {
            employeeService.delete(id);
            return  ResponseUtil.makeSuccessResponse();
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED,"删除");
        }
    }
}
