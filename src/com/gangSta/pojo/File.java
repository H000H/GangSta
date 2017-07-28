package com.gangSta.pojo;

import java.util.Date;

public class File {

	private String email;
	private String filename;
	private long size;
	private Date update;
	private String shareverify;
	private String fileid;
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Date getUpdate() {
		return update;
	}
	public void setUpdate(Date update) {
		this.update = update;
	}
	public String getShareverify() {
		return shareverify;
	}
	public void setShareverify(String shareverify) {
		this.shareverify = shareverify;
	}
	@Override
	public String toString() {
		return "File [email=" + email + ", filename=" + filename + ", size=" + size + ", update=" + update
				+ ", shareverify=" + shareverify + ", fileid=" + fileid + "]";
	}
	
	
	
}
