package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HP on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {


    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public Object getAllEmployees(@RequestParam String departId) {
        return "hello";
    }

    @RequestMapping(value = "addEmployee", method = RequestMethod.POST)
    @Transactional
    public Object addEmployee(@RequestParam String departId) {

        return "success";
    }

    @RequestMapping(value = "updateEmployee", method = RequestMethod.PUT)
    public Object updateEmployee(@RequestParam String departId) {
        return "success";
    }

    @RequestMapping(value = "delEmployee", method = RequestMethod.DELETE)
    public Object delEmployee(@RequestParam String departId) {
        return "success";
    }
}
