package com.hcl.mediclaim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.User;


@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long>{


	



	List<Claim> findByApproverId(User user, Pageable paging);

	Optional<List<Claim>> findByApproverId(User user);





}
