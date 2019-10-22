package com.hcl.mediclaim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.entity.Role;


public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	
	
	Optional<Hospital> findAllByHospitalId(Long hospitalId);
}
