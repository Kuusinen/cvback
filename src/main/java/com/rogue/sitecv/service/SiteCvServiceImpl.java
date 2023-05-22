package com.rogue.sitecv.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rogue.sitecv.dao.CategoryRepository;
import com.rogue.sitecv.dao.ImageRepository;
import com.rogue.sitecv.dao.SectionRepository;
import com.rogue.sitecv.model.Category;
import com.rogue.sitecv.model.Image;
import com.rogue.sitecv.model.Section;

@Service
public class SiteCvServiceImpl implements SiteCvService {

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<Section> getAllSectionsByCategory(final String category) {

		final Optional<Category> categoryFind = categoryRepository.findByName(category);

		if (categoryFind.isPresent()) {
			return Collections.unmodifiableList(sectionRepository.findByCategory(categoryFind.get()));
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void saveSection(final Section section) {
		updateSectionWithGoodCategory(section);
		updateSectionWithGoodImageName(section);

		sectionRepository.save(section);
	}

	private void updateSectionWithGoodCategory(final Section section) {
		final Optional<Category> categoryFind = categoryRepository.findByName(section.getCategory().getName());

		if (categoryFind.isPresent()) {
			section.setCategory(categoryFind.get());
		} else {
			categoryRepository.save(section.getCategory());
		}
	}

	private void updateSectionWithGoodImageName(final Section section) {
		final Optional<Image> imageFind = imageRepository.findByName(section.getImageName());
		
		if (imageFind.isPresent()) {
			section.setImageName(imageFind.get().getName());
		} else {
			section.setImageName("");
		}
	}

	@Override
	public void saveCategory(final Category category) {
		categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public void removeSection(final Section section) {
		final Optional<Section> findSection = sectionRepository.findById(section.getUuid());

		findSection.ifPresent(s -> {
			sectionRepository.delete(s);
			final Optional<Image> findImage = imageRepository.findByName(section.getImageName());

			findImage.ifPresent(image -> {
				imageRepository.delete(image);
			});
		});
	}

	@Override
	public void updateSection(final Section section) {
		final Optional<Section> oldFindSection = sectionRepository.findById(section.getUuid());
		oldFindSection.ifPresent(oldSection -> {
			final Optional<Image> oldFindImage = imageRepository.findByName(oldSection.getImageName());
			oldFindImage.ifPresent(oldImage -> {
				imageRepository.delete(oldImage);
			});
		});

		saveSection(section);
	}
}
