package com.example.Web.controller;

import com.example.Web.dto.APIResponse;
import com.example.Web.dto.EmployeeResponse;
import com.example.Web.entity.EmployeeEntity;
import com.example.Web.repo.EmployeeRepo;
import com.example.Web.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployee();
        return ResponseEntity.ok(employees);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody EmployeeEntity employee) {
        EmployeeEntity savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEmployeeById(@PathVariable Long id, @RequestBody EmployeeEntity employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> deleteByEmployeeById(@PathVariable Long id){
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    };

    //Pagination
    @GetMapping
    private APIResponse<List<EmployeeResponse>> getProducts() {
        List<EmployeeResponse> allProducts = employeeService.getAllEmployee();
        return new APIResponse<>(allProducts.size(), allProducts);
    }

    @GetMapping("/{field}")
    public ResponseEntity<APIResponse<List<EmployeeResponse>>> getProductsWithSort(@PathVariable String field) {
        List<EmployeeEntity> allEmployees = employeeService.findProductsWithSorting(field);
        List<EmployeeResponse> employeeResponses = allEmployees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
        APIResponse<List<EmployeeResponse>> response = new APIResponse<>(employeeResponses.size(), employeeResponses);
        return ResponseEntity.ok(response);
    }

    private EmployeeResponse mapToEmployeeResponse(EmployeeEntity employee) {
        return new EmployeeResponse(employee.getEmpName() , employee.getEmpAddress(),employee.getEmpTelePhone());
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    private APIResponse<Page<EmployeeResponse>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<EmployeeResponse> productsWithPagination = employeeService.findProductsWithPagination(offset, pageSize);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }

    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    private APIResponse<Page<EmployeeResponse>> getProductsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field) {
        Page<EmployeeResponse> productsWithPagination = employeeService.findProductsWithPaginationAndSorting(offset, pageSize, field);
        return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
}
