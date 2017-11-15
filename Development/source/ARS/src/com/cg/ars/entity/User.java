package com.cg.ars.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@SequenceGenerator(name = "seq2", sequenceName = "userid_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq2")
	@Column(name = "userid")
	private int userId;

	@Column(name = "Username")
	@NotBlank(message = "Username is required")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._]{2,20}$", message = "Username(min=3 & max=20) can have only characters,digits, '.(dot)' and '_' and start with an alphabet")
	private String username;

	@Column(name = "Password")
	@NotNull(message = "Please provide a valid password")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})", message = "Password(min=8 & max=20) must contain alteast a small, an uppercase letter, a digit and a special symbol")
	private String pwd;

	@Column(name = "User_email")
	@Email(message = "Email should be valid")
	@NotEmpty(message = "Email is required")
	private String email;

	@Column(name = "Mobile_no")
	@Pattern(regexp = "[1-9][0-9]{9}", message = "Please provide a valid mobile number of 10 digits only")
	private String mobileNo;

	@Column(name = "Role")
	private String role;

	public User() {
		super();
	}

	
	
	public User(int userId, String username, String pwd, String email,
			String mobileNo, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.pwd = pwd;
		this.email = email;
		this.mobileNo = mobileNo;
		this.role = role;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
