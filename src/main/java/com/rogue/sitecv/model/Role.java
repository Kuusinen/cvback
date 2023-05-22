package com.rogue.sitecv.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {

	@Id
	String uuid;

	@Getter
	@Setter
	String name;

	public Role() {
		uuid = UUID.randomUUID().toString();
	}
}
