package com.hcl.mediclaim.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String userName;
	private LocalDate dateOfBirth;
	private String address;
	private String pancardNumber;
	private String aadharNumber;
	private String country;
	private String city;
	private Long pincode;
	private String emailId;
	private Long phoneNumber;
	@ManyToOne
	private Role roleId;
	private String password;

}
