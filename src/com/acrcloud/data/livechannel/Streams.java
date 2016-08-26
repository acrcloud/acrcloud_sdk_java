package com.acrcloud.data.livechannel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Streams {
	private String title;
	private String acrid;
	@JsonProperty("channel_id")
	private String channelId;
	@JsonProperty("timestamp_ms")
	private Long timestampMs;

	public String getAcrid() {
		return acrid;
	}

	public String getChannelId() {
		return channelId;
	}

	public Long getTimestampMs() {
		return timestampMs;
	}

	public String getTitle() {
		return title;
	}

	public void setAcrid(String acrid) {
		this.acrid = acrid;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setTimestampMs(Long timestampMs) {
		this.timestampMs = timestampMs;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
