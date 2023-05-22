package com.rogue.sitecv.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rogue.sitecv.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);

}
