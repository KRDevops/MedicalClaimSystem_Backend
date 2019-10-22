package com.hcl.mediclaim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.mediclaim.entity.Hospital;

import com.hcl.mediclaim.repository.HospitalRepository;



	
public class HospitalServiceImpl implements HospitalService
{
	
	@Autowired
	HospitalRepository hospitalRepository;
	
	

	@Override
	public List<Hospital> listHospitals() {
		
		List<Hospital> hospital = hospitalRepository.findAll();
		
		
		return hospital;
	}
	
}
