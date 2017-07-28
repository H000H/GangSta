package com.gangSta.pojo;

import java.util.Date;

public class Notice {
	
	private String title;
	private String content;
	private Date date;
	private String author;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Notice [title=" + title + ", content=" + content + ", date=" + date + ", author=" + author + ", url="
				+ url + "]";
	}
	
	
}
