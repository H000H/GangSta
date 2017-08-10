package com.gangSta.pojo;


public class MyFile {

	private String email;
	private String filename;
	private long size;
	private String update;
	private String shareverify;
	private String fileid;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
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
		return "MyFile [email=" + email + ", filename=" + filename + ", size=" + size + ", update=" + update
				+ ", shareverify=" + shareverify + ", fileid=" + fileid + "]";
	}
	
	
	
}
