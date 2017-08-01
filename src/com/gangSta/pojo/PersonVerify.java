package com.gangSta.pojo;

public class PersonVerify {

	private String t_email;
	private String t_verify;
	public String getT_email() {
		return t_email;
	}
	public void setT_email(String t_email) {
		this.t_email = t_email;
	}
	public String getT_verify() {
		return t_verify;
	}
	public void setT_verify(String t_verify) {
		this.t_verify = t_verify;
	}
	@Override
	public String toString() {
		return "PersonVerify [t_email=" + t_email + ", t_verify=" + t_verify + "]";
	}
	
	
}
