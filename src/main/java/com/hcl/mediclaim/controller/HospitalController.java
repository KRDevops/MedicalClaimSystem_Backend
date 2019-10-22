package com.hcl.mediclaim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mediclaim.entity.Hospital;
import com.hcl.mediclaim.service.HospitalService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
/**
 * 
 * @author User1
 * @since 2019-10-21 This class includes methods for Hospital list functionality for
 *        approver in mediclaim management system.
 *
 */
public class HospitalController {
	

	@Autowired
	HospitalService hospitalService;
    
	/**
     *  Method used for getting list of hospitals
     * @return
     */
	@GetMapping("/hospitals")
	public ResponseEntity<List<Hospital>> hospitals() {
		log.info("Hospital list started in HOspital controller");
		return new ResponseEntity<>(hospitalService.listHospitals(), HttpStatus.OK);

	}

}
