package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mediclaim.entity.Hospital;


public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	
	
}
