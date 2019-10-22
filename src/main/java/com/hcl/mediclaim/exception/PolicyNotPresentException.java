package com.hcl.mediclaim.exception;

public class PolicyNotPresentException extends Exception {
	private static final long serialVersionUID = 1L;

	public PolicyNotPresentException(String message) {
		super(message);
	}
}
