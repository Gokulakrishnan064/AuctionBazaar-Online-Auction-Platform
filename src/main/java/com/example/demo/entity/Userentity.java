package com.example.demo.entity;



import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="userdata")
public class Userentity {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String firstname;
private String lastname;
private String emailid;
private String phoneno;
private String password;
private String createdby;
private String updatedby;
private Timestamp createdat;
private Timestamp updatedat;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getPhoneno() {
	return phoneno;
}
public void setPhoneno(String phoneno) {
	this.phoneno = phoneno;
}
public String getCreatedby() {
	return createdby;
}
public void setCreatedby(String createdby) {
	this.createdby = createdby;
}
public String getUpdatedby() {
	return updatedby;
}
public void setUpdatedby(String updatedby) {
	this.updatedby = updatedby;
}
public Timestamp getCreatedat() {
	return createdat;
}
public void setCreatedat(Timestamp createdat) {
	this.createdat = createdat;
}
public Timestamp getUpdatedat() {
	return updatedat;
}
public void setUpdatedat(Timestamp updatedat) {
	this.updatedat = updatedat;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public String toString() {
	return "Userentity [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", emailid=" + emailid
			+ ", phoneno=" + phoneno + ", password=" + password + ", createdby=" + createdby + ", updatedby="
			+ updatedby + ", createdat=" + createdat + ", updatedat=" + updatedat + "]";
}


}
