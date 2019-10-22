package com.hcl.mediclaim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.Role;
import com.hcl.mediclaim.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmailIdAndPassword(String emailId, String password);

	List<User> findByRoleId(Role roleId);

	User findByUserId(Long userId);
}
