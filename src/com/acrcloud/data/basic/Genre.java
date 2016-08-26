package com.acrcloud.data.basic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Genre {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
