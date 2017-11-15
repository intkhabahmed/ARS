package com.cg.as.entity;


public class LoginMaster {
	private int userId;
	private String username;
	private String email;
	private String password;
	private String role;
	private long mobile;
	public LoginMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginMaster(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "LoginMaster [userId=" + userId + ", username=" + username
				+ ", password=" + password + ", role=" + role + ", mobile="
				+ mobile + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
