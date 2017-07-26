package com.example.repository;

import com.example.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by HP on 2017/7/26.
 */
public interface IEmployeeInfoRepository extends CrudRepository<EmployeeInfo,Long>,JpaSpecificationExecutor<EmployeeInfo> {
}
