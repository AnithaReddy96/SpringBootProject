package com.spring.repo;

import com.spring.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,Long> {
    LeaveBalance findByEmployeeId(Long employeeId);
}
