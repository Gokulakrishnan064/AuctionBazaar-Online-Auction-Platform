package com.example.demo.entity;



import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
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
}