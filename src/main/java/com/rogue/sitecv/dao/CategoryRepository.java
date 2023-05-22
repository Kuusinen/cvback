package com.rogue.sitecv.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rogue.sitecv.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

	Optional<Category> findByName(String name);
}
