package com.example.Web.service;

import com.example.Web.dto.EmployeeResponse;
import com.example.Web.entity.EmployeeEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService{
    List<EmployeeResponse> getAllEmployee();

    EmployeeEntity saveEmployee(EmployeeEntity employee);

    void updateEmployee(Long id, EmployeeEntity employee);

    void deleteById(Long id);

    public List<EmployeeEntity> findProductsWithSorting(String field);

    public Page<EmployeeResponse> findProductsWithPagination(int offset, int pageSize);

    public Page<EmployeeResponse> findProductsWithPaginationAndSorting(int offset,int pageSize,String field);

}
