package com.hcl.mediclaim.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class Claim implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(initialValue = 20000, name = "claim_id")
	private Long claimId;
	private String diagnosis;
	private LocalDate claimDate;
	private LocalDate admissionDate;
	private LocalDate dischargeDate;
	@ManyToOne
	private Hospital hospitalId;
@Lob
	private String documents;
	private Double claimAmount;
	private Integer deviationPercentage;

	private String claimStatus;
	
	private Long policyNumber;
	private String remarks;
	@ManyToOne
	private User userId;
	@ManyToOne
	private User approverId;
	@ManyToOne
	private User seniorApproverId;
	@Enumerated(EnumType.STRING)
	private Ailment natureOfAilment;
}

enum Ailment {
	CHRONIC, MINOR;
}
