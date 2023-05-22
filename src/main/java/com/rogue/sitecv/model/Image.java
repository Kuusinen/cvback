package com.rogue.sitecv.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

	@Id
	String uuid;

	@Getter
	@Setter
	@Column(unique = true)
	String name;

	@Getter
	@Setter
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	byte[] content;

	@Getter
	@Setter
	String contentType;

	public Image() {
		uuid = UUID.randomUUID().toString();
	}

}
