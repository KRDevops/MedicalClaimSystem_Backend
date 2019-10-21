package com.hcl.mediclaim.service;

import org.springframework.stereotype.Service;

import com.hcl.mediclaim.dto.ApprovalResponseDto;




@Service
public interface ApprovalService {

	ApprovalResponseDto approve(Long approverId,Integer pageNumber);

}
