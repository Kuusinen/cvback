package com.rogue.sitecv.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rogue.sitecv.model.Category;
import com.rogue.sitecv.model.Section;

public interface SectionRepository extends JpaRepository<Section, String> {

	List<Section> findByCategory(Category category);

	Optional<Section> findByTitleAndImageName(String title, String imageName);

}
