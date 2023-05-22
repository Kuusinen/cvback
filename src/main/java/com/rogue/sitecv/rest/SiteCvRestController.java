package com.rogue.sitecv.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rogue.sitecv.model.Category;
import com.rogue.sitecv.model.Section;
import com.rogue.sitecv.service.SiteCvService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SiteCvRestController {

	@Autowired
	private SiteCvService siteCvService;

	@GetMapping(value = "/section")
	public List<Section> getAllSectionsByCategory(@RequestParam(name = "cat") final String category) {
		return siteCvService.getAllSectionsByCategory(category);
	}

	@PostMapping(value = "/section")
	public void createSection(@RequestBody final Section section) {
		siteCvService.saveSection(section);
	}

	@DeleteMapping(value = "/section/remove")
	public void deleteSection(@RequestBody final Section section) {
		siteCvService.removeSection(section);
	}

	@PutMapping(value="/section/update")
	public void updateSection(@RequestBody final Section section) {
		siteCvService.updateSection(section);
	}

	@PostMapping(value = "/category")
	public void createCategory(@RequestBody final Category category) {
		siteCvService.saveCategory(category);
	}

	@GetMapping(value = "/category")
	public List<Category> getAllCategory() {
		return siteCvService.getAllCategory();
	}
}
