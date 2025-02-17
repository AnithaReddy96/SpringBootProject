package com.spring.service;

import com.spring.entity.LeaveBalance;
import com.spring.entity.LeaveRequest;
import com.spring.repo.LeaveBalanceRepository;
import com.spring.repo.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveRequestService(LeaveRequestRepository requestRepo, LeaveBalanceRepository balanceRepo) {
        this.leaveRequestRepository = requestRepo;
        this.leaveBalanceRepository = balanceRepo;
    }
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest applyForLeave(LeaveRequest leaveRequest) {
        System.out.println("Employee id   **** "+leaveRequest.getEmployee().getId());
        LeaveBalance leaveBalance = findByEmployeeId(leaveRequest.getEmployee().getId());
        if (leaveBalance == null) { // If no leave balance, create one
            leaveBalance = new LeaveBalance();
            leaveBalance.setEmployee(leaveRequest.getEmployee());
            leaveBalance.setTotalLeaves(20);
            leaveBalance.setLeavesUsed(0);
            leaveBalanceRepository.save(leaveBalance);
        }

        int requestedDays = leaveRequest.getEndDate().getDayOfYear() - leaveRequest.getStartDate().getDayOfYear();

        if (leaveBalance.getTotalLeaves() - leaveBalance.getLeavesUsed() >= requestedDays) {
            leaveBalance.setLeavesUsed(leaveBalance.getLeavesUsed() + requestedDays);
            leaveRequest.setStatus("APPROVED");
            leaveBalanceRepository.save(leaveBalance);
            return leaveRequestRepository.save(leaveRequest);
        } else {
            leaveRequest.setStatus("REJECTED");
            leaveBalanceRepository.save(leaveBalance);
            return leaveRequestRepository.save(leaveRequest);
        }
    }

   /* public LeaveRequest approveLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus("APPROVED");
        return leaveRequestRepository.save(leaveRequest);
    }
    public LeaveRequest rejectLeave(Long requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus("REJECTED");
        return leaveRequestRepository.save(leaveRequest);
    }*/

    public LeaveBalance findByEmployeeId(Long id){
        return leaveBalanceRepository.findByEmployeeId(id);
    }

}
