package com.example.service;

import com.example.entity.EmployeeInfo;
import com.example.repository.IEmployeeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * Created by HP on 2017/7/26.
 */
public class EmployeeService {

    @Autowired
    private IEmployeeInfoRepository employeeInfoRepository;

    public void save(EmployeeInfo entity)
    {
        employeeInfoRepository.save(entity);
    }

    public void delete(long id)
    {
        employeeInfoRepository.delete(id);
    }

    public EmployeeInfo findOne(long id)
    {
        return employeeInfoRepository.findOne(id);
    }

    public Page<EmployeeInfo> findPageable()
    {
        return null;
    }
}
