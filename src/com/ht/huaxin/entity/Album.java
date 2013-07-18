package com.ht.huaxin.entity;

import java.io.Serializable;

import com.ht.huaxin.database.dao.impl.AlbumDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(daoClass = AlbumDaoImpl.class)
public class Album implements Serializable {

	private static final long serialVersionUID = 6687085779132432840L;
 
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String profile_img_url;
	@DatabaseField
	private Integer picture_count;
	@DatabaseField
	private String author_intro;
	@DatabaseField
	private String album_intro;
	@DatabaseField
	private String create_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfile_img_url() {
		return profile_img_url;
	}

	public void setProfile_img_url(String profile_img_url) {
		this.profile_img_url = profile_img_url;
	}

	public Integer getPicture_count() {
		return picture_count;
	}

	public void setPicture_count(Integer picture_count) {
		this.picture_count = picture_count;
	}

	public String getAuthor_intro() {
		return author_intro;
	}

	public void setAuthor_intro(String author_intro) {
		this.author_intro = author_intro;
	}

	public String getAlbum_intro() {
		return album_intro;
	}

	public void setAlbum_intro(String album_intro) {
		this.album_intro = album_intro;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
