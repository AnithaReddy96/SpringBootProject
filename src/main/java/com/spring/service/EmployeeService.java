package com.spring.service;

import com.spring.entity.Employee;
import com.spring.entity.LeaveBalance;
import com.spring.repo.EmployeeRepository;
import com.spring.repo.LeaveBalanceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public EmployeeService(EmployeeRepository employeeRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.repo = employeeRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }
    public List<Employee> getAllEmployees() {
      return  repo.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

    }

    public Employee addEmployee(@Valid Employee employee) {
        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setEmployee(employee);
        leaveBalance.setTotalLeaves(20); // Ensure default leaves
        leaveBalance.setLeavesUsed(0);

        leaveBalanceRepository.save(leaveBalance);
        return repo.save(employee);
    }

    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
    public Employee updateEmployee(Long id,Employee employee){
        Employee emp = repo.findById(id).orElseThrow(()-> new RuntimeException("No Employee found"));
        emp.setEmail(employee.getEmail());
        emp.setName(employee.getName());
        return repo.save(employee);
    }
}
