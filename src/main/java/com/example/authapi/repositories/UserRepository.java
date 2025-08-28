package com.example.authapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.authapi.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
	
	@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.phones")
	List<User> findAll();
}
