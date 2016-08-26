package com.acrcloud.data.basic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Musicstory {
	private Album album;
	private Release release;
	private Track track;

	public Album getAlbum() {
		return album;
	}

	public Release getRelease() {
		return release;
	}

	public Track getTrack() {
		return track;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	public void setTrack(Track track) {
		this.track = track;
	}
}
