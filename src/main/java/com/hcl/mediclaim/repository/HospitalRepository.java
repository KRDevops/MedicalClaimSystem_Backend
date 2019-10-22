package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Hospital;



@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>{


}
