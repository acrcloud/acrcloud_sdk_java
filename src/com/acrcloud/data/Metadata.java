package com.acrcloud.data;

import java.util.List;

import com.acrcloud.data.customfile.CustomFiles;
import com.acrcloud.data.humming.Humming;
import com.acrcloud.data.livechannel.Streams;
import com.acrcloud.data.music.Music;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Metadata {
	// TODO date FIXME
	@JsonProperty("timestamp_utc")
	private String timestampUtc;
	private List<Music> music;
	private List<Streams> streams;
	private List<Humming> humming;
	@JsonProperty("custom_files")
	private List<CustomFiles> customFiles;

	public List<CustomFiles> getCustomFiles() {
		return customFiles;
	}

	public List<Humming> getHumming() {
		return humming;
	}

	public List<Music> getMusic() {
		return music;
	}

	public List<Streams> getStreams() {
		return streams;
	}

	public String getTimestampUtc() {
		return timestampUtc;
	}

	public void setHumming(List<Humming> humming) {
		this.humming = humming;
	}

	public void setMusic(List<Music> music) {
		this.music = music;
	}

	public void setStreams(List<Streams> streams) {
		this.streams = streams;
	}

	public void setTimestampUtc(String timestampUtc) {
		this.timestampUtc = timestampUtc;
	}
}
