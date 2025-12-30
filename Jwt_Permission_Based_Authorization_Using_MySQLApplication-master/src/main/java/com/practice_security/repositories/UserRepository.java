package com.practice_security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice_security.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByEmail(String email);

}