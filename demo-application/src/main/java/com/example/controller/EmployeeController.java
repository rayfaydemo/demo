package com.example.controller;

import com.example.client.ApiClient;
import com.example.entity.EmployeeInfo;
import com.example.message.SystemErrorCodeType;
import com.example.service.EmployeeService;
import com.example.utils.ResponseUtil;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "getPageable", method = RequestMethod.GET)
    public Object getEmployeesPageable(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                       @RequestParam(name = "name", required = false) String name) {
        logger.info("parameters:pageSize={},pageNumber={},name={}",pageSize,pageNumber,name);
        try {
            Page<EmployeeInfo> result = employeeService.findPageable(pageSize,pageNumber-1,name);
            return ResponseUtil.makeSuccessResponse(result.getTotalElements(), result.getContent());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new NRAPException(SystemErrorCodeType.E_GET_DATA_FALED);
        }
       // return apiClient.getEmployeePageable(pageSize,pageNumber,name);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object getEmployeeInfo(@RequestParam long id) {
        logger.info("paras:id={}",id);
        try {
            List<EmployeeInfo> result = new ArrayList<>();
            EmployeeInfo info = employeeService.findOne(id);
            if(null != result)
            {
                result.add(info);
            }
            return ResponseUtil.makeSuccessResponse(result.size(), result);
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_GET_DATA_FALED);
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    //@Transactional
    public Object addEmployee(@RequestBody EmployeeInfo data) {
        try {
            employeeService.save(data);
            return  ResponseUtil.makeSuccessResponse();
        } catch (Exception e) {
            throw new NRAPException(SystemErrorCodeType.E_ACTION_FALED,e.getMessage()+"增加");
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Object updateEmployee(@RequestBody EmployeeInfo data) {
        try {
            EmployeeInfo dbData = employeeService.findOne(data.getId());
            dbData.setName(data.getName());
            dbData.setAddress(data.getAddress());
            dbData.setSex(data.getSex());
            dbData.setTelNumber(data.getTelNumber());
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
