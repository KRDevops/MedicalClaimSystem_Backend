package com.hcl.mediclaim.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Claim;
import com.hcl.mediclaim.entity.User;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

	List<Claim> findByApproverId(User user, Pageable paging);

	Optional<Claim> findByClaimId(Long claimId);

	@Transactional
	@Modifying
	@Query("UPDATE Claim c SET c.claimStatus = :claimStatus, c.remarks=:remarks WHERE c.claimId = :claimId")
	void updateClaimStatusAndRemarksByClaimId(@Param("claimId") Long claimId, @Param("claimStatus") String claimStatus,
			@Param("remarks") String remarks);

	@Transactional
	@Modifying
	@Query("UPDATE Claim c SET c.claimStatus = :claimStatus , c.remarks=:remarks , c.seniorApproverId=:seniorApproverId WHERE c.id = :claimId")
	void updateClaimStatusAndSeniorApproverIdAndRemarksByClaimId(@Param("seniorApproverId") Long seniorApproverId,
			@Param("claimId") Long claimId, @Param("claimStatus") String claimStatus, @Param("remarks") String remarks);

	List<Claim> findByApproverId(User user);
	
	Claim findTopByOrderByClaimIdDesc();

}
