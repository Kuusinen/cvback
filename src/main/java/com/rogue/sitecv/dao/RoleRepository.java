package com.rogue.sitecv.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rogue.sitecv.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

	Optional<Role> findByName(String name);
}
