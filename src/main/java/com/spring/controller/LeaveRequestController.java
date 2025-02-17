package com.spring.controller;

import com.spring.entity.LeaveRequest;
import com.spring.service.LeaveRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @PostMapping("/apply")
    public ResponseEntity<LeaveRequest> applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            throw new RuntimeException("Leave request cannot be null.");
        }
        LeaveRequest processedRequest = leaveRequestService.applyForLeave(leaveRequest);
        return new ResponseEntity<>(processedRequest, HttpStatus.OK);
    }

   /* @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id) {
        LeaveRequest updatedLeave = leaveRequestService.approveLeave(id);
        return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id) {
        LeaveRequest updatedLeave = leaveRequestService.rejectLeave(id);
        return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
    }*/
}

