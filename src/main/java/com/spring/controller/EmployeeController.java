package com.spring.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import com.spring.service.EmployeeService;
import com.spring.entity.Employee;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

        @Autowired
        private EmployeeService service;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return service.getAllEmployees();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
            return ResponseEntity.ok(service.getEmployeeById(id));
        }

        @PostMapping
        public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
            return new ResponseEntity<>(service.addEmployee(employee), HttpStatus.CREATED);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
            service.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@Valid @RequestBody Employee employee){
           return new ResponseEntity<>(service.updateEmployee(id,employee),HttpStatus.OK);      }
}

