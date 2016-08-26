package com.acrcloud.data.basic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ExternalMetadata {
	private Itune itunes;
	private Spotify spotify;
	private Youtube youtube;
	private Musicstory musicstory;
	private Deezer deezer;

	public Deezer getDeezer() {
		return deezer;
	}

	public Itune getItunes() {
		return itunes;
	}

	public Musicstory getMusicstory() {
		return musicstory;
	}

	public Spotify getSpotify() {
		return spotify;
	}

	public Youtube getYoutube() {
		return youtube;
	}

	public void setDeezer(Deezer deezer) {
		this.deezer = deezer;
	}

	public void setItunes(Itune itunes) {
		this.itunes = itunes;
	}

	public void setMusicstory(Musicstory musicstory) {
		this.musicstory = musicstory;
	}

	public void setSpotify(Spotify spotify) {
		this.spotify = spotify;
	}

	public void setYoutube(Youtube youtube) {
		this.youtube = youtube;
	}
}
