package com.example.Web.serviceImpl;

import com.example.Web.dto.EmployeeResponse;
import com.example.Web.entity.EmployeeEntity;
import com.example.Web.exception.EmployeeUnableToSaveException;
import com.example.Web.exception.EmployeeUnableToUpdateException;
import com.example.Web.exception.EmployeesNotFoundException;
import com.example.Web.repo.EmployeeRepo;
import com.example.Web.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public EmployeeRepo employeeRepo;

    private EmployeeResponse mapToEmployeeResponse(EmployeeEntity employee) {
        return new EmployeeResponse(employee.getEmpAddress(), employee.getEmpName(), employee.getEmpTelePhone());
    }

    private EmployeeEntity maptToEmployeeEntity(EmployeeResponse employeeResponse){
        return new EmployeeEntity(employeeResponse.getEmpName(), employeeResponse.getEmpAddress(), employeeResponse.getEmpTelePhone());
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        List<EmployeeEntity> employees = employeeRepo.findAll();

        if (employees.isEmpty()) {
            throw new EmployeesNotFoundException("No employees found", "NO_MORE_EMPLOYEES");
        }

        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public EmployeeEntity saveEmployee(EmployeeEntity employee) {
        try {
            return employeeRepo.save(employee);
        } catch (Exception e) {
            throw new EmployeeUnableToSaveException("Unable to save employee","UNABLE_TO_SAVE");
        }
    }

    @Override
    public void updateEmployee(Long id, EmployeeEntity employee) {
        EmployeeEntity existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeUnableToUpdateException("Couldn't update employee with id " + id, "COUDNT_UPDATE_EMPLOYEE"));

        existingEmployee.setEmpAddress(employee.getEmpAddress());
        existingEmployee.setEmpName(employee.getEmpName());
        existingEmployee.setEmpTelePhone(employee.getEmpTelePhone());

        employeeRepo.save(existingEmployee);
        log.info("Employee Updated by id: {}", id);
    }

    @Override
    public void deleteById(Long id) {
        EmployeeEntity existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeesNotFoundException("Unable to delete employee with id " + id, "UNABLE_TO_DELETE"));

        employeeRepo.deleteById(id);
        log.info("Employee deleted by id: {}", id);
    }

    @Override
    public List<EmployeeEntity> findProductsWithSorting(String field){
        return  employeeRepo.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    @Override
    public Page<EmployeeResponse> findProductsWithPagination(int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        Page<EmployeeEntity> employees = employeeRepo.findAll(pageable);
        List<EmployeeResponse> employeeResponses = employees.getContent().stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(employeeResponses, pageable, employees.getTotalElements());
    }


    public Page<EmployeeResponse> findProductsWithPaginationAndSorting(int offset, int pageSize, String field) {
        Pageable pageable = PageRequest.of(offset, pageSize).withSort(Sort.by(field));
        Page<EmployeeEntity> employees = employeeRepo.findAll(pageable);
        List<EmployeeResponse> employeeResponses = employees.getContent().stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(employeeResponses, pageable, employees.getTotalElements());
    }


}


