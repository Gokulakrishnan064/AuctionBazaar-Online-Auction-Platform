package com.example.demo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Userentity;
import com.example.demo.model.Save_user_model;
import com.example.demo.model.Update_user;
import com.example.demo.model.User_login;
import com.example.demo.model.User_response_model;
import com.example.demo.serice.Userservice;
import com.example.demo.serice.impl.Userserviseimplementation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class Usercontroller {
	@Autowired
	private Userserviseimplementation userImpl;
	@PostMapping("/signup")
	public ResponseEntity<User_response_model> saveUsers(@RequestBody Save_user_model user){
	User_response_model userRespModel=new User_response_model();
	Userentity newUser=new Userentity();
	newUser.setFirstname(user.getFirstName());
	newUser.setLastname(user.getLastName());
	newUser.setEmailid(user.getEmail());
	newUser.setPhoneno(user.getPhoneNumber());
	newUser.setPassword(user.getPassword());
	
	Userentity savedUser = userImpl.saveUser(newUser);
	if(savedUser!=null) {
		userRespModel.setUser(savedUser);
		userRespModel.setResponseMessage(HttpStatus.OK.name());
		userRespModel.setResponseCode(HttpStatus.OK.value());
		userRespModel.setStatusMessage("User has been created successfully");
	}else {
		userRespModel.setUser(null);
		userRespModel.setResponseMessage(HttpStatus.ALREADY_REPORTED.name());
		userRespModel.setResponseCode(HttpStatus.ALREADY_REPORTED.value());
		userRespModel.setStatusMessage("User Aldready exist with email id : "+user.getEmail());
	}
	userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
	return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);	
	}	
@GetMapping("/deleteuser/{Id}")
    public ResponseEntity<User_response_model> deleteUser(@PathVariable int Id) {
		User_response_model userRespModel = new User_response_model();
		Userentity deletedUser = userImpl.deleteUser(Id); 
		if(deletedUser!=null) {
			userRespModel.setUser(null);
			userRespModel.setResponseMessage(HttpStatus.OK.name());
			userRespModel.setResponseCode(HttpStatus.OK.value());
			String name = deletedUser.getFirstname()+" "+deletedUser.getLastname();
			userRespModel.setStatusMessage("User "+name+" has been deleted sucessfully");
		}else {
			userRespModel.setUser(null);
			userRespModel.setResponseMessage(HttpStatus.NOT_FOUND.name());
			userRespModel.setResponseCode(HttpStatus.NOT_FOUND.value());
			userRespModel.setStatusMessage("User Not Found With UserId : "+Id);
		}
		userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);
	}

@PostMapping("/updateuser")
public ResponseEntity<User_response_model> updateUser(@RequestBody Update_user user){
	User_response_model userRespModel = new User_response_model();
	Userentity updatedUser = new Userentity();
	updatedUser.setFirstname(user.getFirstName()!=null?user.getFirstName():null);
	updatedUser.setLastname(user.getLastName()!=null?user.getLastName():null);
	updatedUser.setId(user.getId());
	updatedUser.setPhoneno(user.getPhoneNumber()!=null?user.getPhoneNumber():null);
	updatedUser.setPassword(user.getPassword());
	Userentity newUser = userImpl.updateUser(updatedUser);
	if(newUser!=null) {
		userRespModel.setUser(newUser);
		userRespModel.setResponseMessage(HttpStatus.OK.name());
		userRespModel.setResponseCode(HttpStatus.OK.value());
		userRespModel.setStatusMessage("User Data updated successfully");
	}else {
		userRespModel.setUser(null);
		userRespModel.setResponseMessage(HttpStatus.NOT_FOUND.name());
		userRespModel.setResponseCode(HttpStatus.NOT_FOUND.value());
		userRespModel.setStatusMessage("Error has occures");
	}
	userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
	return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);	
}

@PostMapping("/login")
public ResponseEntity<User_response_model> authandicateUsers(@RequestBody User_login user){
	User_response_model userRespModel = new User_response_model();
	Userentity authUser = userImpl.authandicateUser(user.getEmail(), user.getPassword());
	if(authUser!=null) {
		userRespModel.setUser(authUser);
		userRespModel.setResponseMessage(HttpStatus.OK.name());
		userRespModel.setResponseCode(HttpStatus.OK.value());
		userRespModel.setStatusMessage("Login Successfull!");
	}else {
		userRespModel.setUser(null);
		userRespModel.setResponseMessage(HttpStatus.NOT_FOUND.name());
		userRespModel.setResponseCode(HttpStatus.NOT_FOUND.value());
		userRespModel.setStatusMessage("Username or password is wrong");
	}
	userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
	return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);
}
}

