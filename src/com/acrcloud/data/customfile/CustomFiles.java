package com.acrcloud.data.customfile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class CustomFiles {
	@JsonProperty("bucket_id")
	private String bucketId;
	@JsonProperty("play_offset_ms")
	private Integer playOffsetMs;
	private String title;
	private String artist;
	private String acrid;
	@JsonProperty("audio_id")
	private String audioId;

	public String getAcrid() {
		return acrid;
	}

	public String getArtist() {
		return artist;
	}

	public String getAudioId() {
		return audioId;
	}

	public String getBucketId() {
		return bucketId;
	}

	public Integer getPlayOffsetMs() {
		return playOffsetMs;
	}

	public String getTitle() {
		return title;
	}

	public void setAcrid(String acrid) {
		this.acrid = acrid;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}

	public void setPlayOffsetMs(Integer playOffsetMs) {
		this.playOffsetMs = playOffsetMs;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
