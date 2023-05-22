package com.rogue.sitecv.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Category {

	@Id
	String uuid;

	String name;

	public Category() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Category(final String name) {
		this.uuid = UUID.randomUUID().toString();
		this.name = name;
	}
}
