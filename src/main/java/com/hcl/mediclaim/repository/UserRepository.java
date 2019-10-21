package com.hcl.mediclaim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mediclaim.entity.User;


@Repository

public interface UserRepository  extends JpaRepository<User, Long>
{
	User findById(long userId);

	

}
