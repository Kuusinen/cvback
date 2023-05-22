package com.rogue.sitecv.service;

import java.util.List;

import com.rogue.sitecv.model.Category;
import com.rogue.sitecv.model.Section;


public interface SiteCvService {

	List<Section> getAllSectionsByCategory(String category);

	void saveSection(Section section);

	void saveCategory(Category category);

	List<Category> getAllCategory();

	void removeSection(Section section);

	void updateSection(Section section);
}
