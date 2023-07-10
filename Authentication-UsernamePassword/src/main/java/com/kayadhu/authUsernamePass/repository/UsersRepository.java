package com.kayadhu.authUsernamePass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kayadhu.authUsernamePass.models.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);

}
