package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long>{

}
