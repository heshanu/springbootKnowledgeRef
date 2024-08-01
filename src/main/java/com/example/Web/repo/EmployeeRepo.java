package com.example.Web.repo;

import com.example.Web.dto.EmployeeResponse;
import com.example.Web.entity.EmployeeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity,Float> {
    @Query("select e from Employee e")
    List<EmployeeResponse> findAllEmployeesQuery();

    @Query("select e from Employee e where e.active=:activeState and e.designation in :designationList")
    List<EmployeeResponse> findEmployeeByDesignationAndActiveQuery(@Param("activeState") Boolean activeState,
                                                           @Param("designationList") List<String> designationList,
                                                           Sort sort);
    @Modifying
    @Query("update Employee e set e.active= ?1 where e.empId in ?2")
    int updateEmployeeStateByEmployeeId(Boolean activeState,List<Long> empIdList);
}
