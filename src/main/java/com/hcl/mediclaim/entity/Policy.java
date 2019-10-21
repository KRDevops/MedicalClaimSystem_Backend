package com.hcl.mediclaim.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class Policy implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(initialValue = 10000, name = "policy_number")
	private Long policyNumber;
	private String policyName;
	private Double entitledAmount;
	private Double availableAmount;
	@Enumerated(EnumType.STRING)
	private Status status;
	private LocalDate expiryDate;
	@ManyToOne
	private User userId;
}

enum Status {
	ACTIVE, INACTIVE
}