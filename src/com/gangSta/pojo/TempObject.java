package com.gangSta.pojo;

import java.util.List;

public class TempObject {
	private int pagenumber;
	private List<MyFile> list;
	public TempObject(int p,List<MyFile> list) {
		this.pagenumber=p;
		this.list=list;
	}
	public int getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	public List<MyFile> getList() {
		return list;
	}
	public void setList(List<MyFile> list) {
		this.list = list;
	}
	
}
