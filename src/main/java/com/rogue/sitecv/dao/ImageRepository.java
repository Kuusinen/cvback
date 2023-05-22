package com.rogue.sitecv.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rogue.sitecv.model.Image;

public interface ImageRepository extends JpaRepository<Image, String> {

	Optional<Image> findByName(String name);
}
