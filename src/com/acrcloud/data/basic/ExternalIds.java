package com.acrcloud.data.basic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ExternalIds {
	private String upc;
	private String isrc;

	public String getIsrc() {
		return isrc;
	}

	public String getUpc() {
		return upc;
	}

	public void setIsrc(String isrc) {
		this.isrc = isrc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}
}
