package com.gangSta.pojo;

import java.util.List;

public class Admin {
	private int page;
	private Person person;
	private List<Person> list;
	private Disk disk;
	public Admin(int page,Person person,List<Person> list,Disk disk) {
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
	public List<Person> getList() {
		return list;
	}
	public void setList(List<Person> list) {
		this.list = list;
	}
	public String toxxx(){
		String str=page+person.getEmail()+list.get(0).getEmail()+disk.getAll();
		return str;
	}
}
