package com.example.Web.serviceImpl;

import com.example.Web.dto.EmployeeResponse;
import com.example.Web.entity.EmployeeEntity;
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
        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeEntity saveEmployee(EmployeeEntity employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public void updateEmployee(Long id, EmployeeEntity employee) {
            Optional<EmployeeEntity> existingEmployee = employeeRepo.findById(Float.valueOf(id));
            if (existingEmployee.isPresent()) {
                EmployeeEntity updatedEmployee = existingEmployee.get();
                updatedEmployee.setEmpAddress(employee.getEmpAddress());
                updatedEmployee.setEmpName(employee.getEmpName());
                updatedEmployee.setEmpTelePhone(employee.getEmpTelePhone());

                employeeRepo.save(updatedEmployee);
                log.info("Emplyee Updated by id",id);
            } else {
                throw new RuntimeException("Employee not found");
            }
        }

    public void deleteById(Long id) {
        Optional<EmployeeEntity> existingEmployee = employeeRepo.findById(Float.valueOf (id));
        if (existingEmployee.isPresent()) {
            employeeRepo.deleteById(Float.valueOf (id));
            log.info("Employee deleted by id: {}", id);
        } else {
            throw new RuntimeException("Employee not found");
        }
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


