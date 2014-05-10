package com.droidboosters.teachaids.models;

import java.io.Serializable;

/**
 * 
 * Model class for videos object
 */
@SuppressWarnings("serial")
public class VideosModel implements Serializable {
	private String id;
	private String url;
	private String thumbnail;
	private String title;

	public VideosModel() {

	}

	public VideosModel(String id, String url, String thumbnail, String title) {
		this.id = id;
		this.url = url;
		this.thumbnail = thumbnail;
		this.title = title;

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
