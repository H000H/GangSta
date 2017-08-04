package com.gangSta.pojo;

import java.util.List;

public class User {
	private int page;
	private Person person;
	private List<MyFile> list;
	private Disk disk;
	public User(int page,Person person,List<MyFile> list,Disk disk) {
		this.disk = disk;this.page = page;this.person = person;this.list = list;
	}
	
	public Disk getDisk() {
		return disk;
	}

	public void setDisk(Disk disk) {
		this.disk = disk;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<MyFile> getList() {
		return list;
	}
	public void setList(List<MyFile> list) {
		this.list = list;
	}
	public String toxxx(){
		String str=page+person.getEmail()+list.get(0).getFilename()+disk.getAll();
		return str;
	}
}
