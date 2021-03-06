package com.test.web.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.web.entities.User;

public interface UserRepository extends CommonRepository<User>
{
	Optional<User> findByUserIdAndPassword(String userId, String password);
	
	Page<User> findAll(Pageable pageable);
}
