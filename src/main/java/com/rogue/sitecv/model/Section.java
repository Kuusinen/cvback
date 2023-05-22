package com.rogue.sitecv.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Section {

	@Id
	String uuid;

	@OneToOne
	Category category;

	String title;

	String body;

	String date;

	String imageName;

	public Section() {
		uuid = UUID.randomUUID().toString();
	}
}
