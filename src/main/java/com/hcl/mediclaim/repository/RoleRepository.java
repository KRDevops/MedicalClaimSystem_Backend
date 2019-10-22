package com.hcl.mediclaim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.enums.RoleNames;
import com.hcl.mediclaim.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByRoleName(RoleNames roleName);
	
}
