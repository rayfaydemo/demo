package com.example.service;

import com.example.entity.EmployeeInfo;
import com.example.repository.IEmployeeInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/7/26.
 */
@Service("EmployeeService")
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

    public Page<EmployeeInfo> findPageable(int pageSize, int pageNumber, String name)
    {
        PageRequest pageRequest = new PageRequest(pageNumber,pageSize);
        return employeeInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }, pageRequest);
    }
}
