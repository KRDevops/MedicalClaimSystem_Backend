package com.hcl.mediclaim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.mediclaim.entity.Hospital;

import com.hcl.mediclaim.repository.HospitalRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author User1
 * 
 *
 */

@Service	
@Slf4j
public class HospitalServiceImpl implements HospitalService
{
	
	@Autowired
	HospitalRepository hospitalRepository;
	
	/**
	 * @return 
	 */
	@Override
	public List<Hospital> listHospitals() {
		
		List<Hospital> hospital = hospitalRepository.findAll();
		log.info("Method used to fetch list of hospitals");

		
		return hospital;
	}
	
}
