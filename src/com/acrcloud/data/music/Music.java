package com.acrcloud.data.music;

import java.util.List;

import com.acrcloud.data.basic.Album;
import com.acrcloud.data.basic.Artist;
import com.acrcloud.data.basic.ExternalIds;
import com.acrcloud.data.basic.ExternalMetadata;
import com.acrcloud.data.basic.Genre;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Music {
	@JsonProperty("duration_ms")
	private Long durationMs;
	private List<Genre> genres;
	@JsonProperty("release_date")
	private String releaseDate;
	@JsonProperty("external_ids")
	private ExternalIds externalIds;
	@JsonProperty("external_metadata")
	private ExternalMetadata externalMetadata;
	private String title;
	@JsonProperty("play_offset_ms")
	private Integer playOffsetMs;
	private String acrid;
	private String label;
	private Album album;
	private List<Artist> artists;

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

	public List<Genre> getGenres() {
		return genres;
	}

	public String getLabel() {
		return label;
	}

	public Integer getPlayOffsetMs() {
		return playOffsetMs;
	}

	public String getReleaseDate() {
		return releaseDate;
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

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setPlayOffsetMs(Integer playOffsetMs) {
		this.playOffsetMs = playOffsetMs;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
