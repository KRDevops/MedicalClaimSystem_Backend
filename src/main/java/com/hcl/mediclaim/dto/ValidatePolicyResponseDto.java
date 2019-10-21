package com.hcl.mediclaim.dto;

import java.time.LocalDate;

public class ValidatePolicyResponseDto {
	private String message;
	private int statusCode;
	private double availableAmount;
	private String policyName;
	private double entitledAmount;
	private String status;
	private LocalDate expiryDate;
	private Long userId;
	public String getMessage() {
		return message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public double getAvailableAmount() {
		return availableAmount;
	}
	public String getPolicyName() {
		return policyName;
	}
	public double getEntitledAmount() {
		return entitledAmount;
	}
	public String getStatus() {
		return status;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public long getUserId() {
		return userId;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public void setEntitledAmount(double entitledAmount) {
		this.entitledAmount = entitledAmount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	}
