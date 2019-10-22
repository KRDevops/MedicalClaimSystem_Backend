package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{

}
