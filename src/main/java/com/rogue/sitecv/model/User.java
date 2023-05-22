package com.rogue.sitecv.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

	@Id
	String uuid;

	@Getter
	@Setter
	@Column(unique = true)
	String name;

	@Getter
	@Setter
	String password;

	@Getter
	@Setter
	@OneToOne
	Role role;

	public User() {
		uuid = UUID.randomUUID().toString();
	}
}
