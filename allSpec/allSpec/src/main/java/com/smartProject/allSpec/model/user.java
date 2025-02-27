package com.smartProject.allSpec.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class user {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<contact> contacts) {
		this.contacts = contacts;
	}
	
	private String email;
	private String password;
	private String role;
	@Column(length=500)
	private String about;
//	@Override
//	public String toString() {
//		return "user [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ ", about=" + about + ", imageUrl=" + imageUrl + ", enabled=" + enabled + ", contacts=" + contacts
//				+ "]";
//	}
	private String imageUrl;
	private boolean enabled;
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	private List<contact> contacts=new ArrayList<>();
	
}
