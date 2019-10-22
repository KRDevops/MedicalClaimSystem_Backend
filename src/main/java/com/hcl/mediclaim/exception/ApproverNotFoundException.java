package com.hcl.mediclaim.exception;

import java.io.Serializable;

public class ApproverNotFoundException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	public ApproverNotFoundException(String message) {
		super(message);
	}
}
