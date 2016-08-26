package com.acrcloud.data.basic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Artist {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
