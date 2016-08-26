package com.acrcloud.data.humming;

import java.util.List;

import com.acrcloud.data.basic.Album;
import com.acrcloud.data.basic.Artist;
import com.acrcloud.data.basic.ExternalIds;
import com.acrcloud.data.basic.ExternalMetadata;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Humming {
	@JsonProperty("duration_ms")
	private Long durationMs;
	@JsonProperty("external_ids")
	private ExternalIds externalIds;
	private String title;
	private List<Artist> artists;
	@JsonProperty("external_metadata")
	private ExternalMetadata externalMetadata;
	private String acrid;
	private Album album;
	private String score;

	public String getAcrid() {
		return acrid;
	}

	public Album getAlbum() {
		return album;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public Long getDurationMs() {
		return durationMs;
	}

	public ExternalIds getExternalIds() {
		return externalIds;
	}

	public ExternalMetadata getExternalMetadata() {
		return externalMetadata;
	}

	public String getScore() {
		return score;
	}

	public String getTitle() {
		return title;
	}

	public void setAcrid(String acrid) {
		this.acrid = acrid;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public void setDurationMs(Long durationMs) {
		this.durationMs = durationMs;
	}

	public void setExternalIds(ExternalIds externalIds) {
		this.externalIds = externalIds;
	}

	public void setExternalMetadata(ExternalMetadata externalMetadata) {
		this.externalMetadata = externalMetadata;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
