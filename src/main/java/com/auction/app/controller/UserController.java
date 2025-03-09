package com.auction.app.controller;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auction.app.entity.UserEntity;
import com.auction.app.model.SaveUserModel;
import com.auction.app.model.UpdateUserModel;
import com.auction.app.model.UserAuthRequestModel;
import com.auction.app.model.UserFindAllRespModel;
import com.auction.app.model.UserResponseModel;

import com.auction.app.service.impl.UserServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServiceImplementation userService;
	
	@PostMapping("/signup")
	public ResponseEntity<UserResponseModel> saveUsers(@RequestBody SaveUserModel user){
		UserResponseModel userRespModel = new UserResponseModel();
		UserEntity newUser = new UserEntity();
		newUser.setUserName(user.getUsername());
		newUser.setFirstName(user.getFirstname());
		newUser.setLastName(user.getLastname());
		newUser.setEmail(user.getEmail());
		newUser.setPhoneNumber(null);
		UserEntity savedUser = userService.saveUser(newUser,user.getPassword());
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
	
	@GetMapping("/delete-user/{Id}")
	public ResponseEntity<UserResponseModel> deleteUser(@PathVariable int Id) {
		UserResponseModel userRespModel = new UserResponseModel();
		UserEntity deletedUser = userService.deleteUser(Id); 
		if(deletedUser!=null) {
			userRespModel.setUser(null);
			userRespModel.setResponseMessage(HttpStatus.OK.name());
			userRespModel.setResponseCode(HttpStatus.OK.value());
			String name = deletedUser.getFirstName()+" "+deletedUser.getLastName();
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
	@PostMapping("/login")
	public ResponseEntity<UserResponseModel> authenticateUser(@RequestBody UserAuthRequestModel user){
		UserResponseModel userRespModel = new UserResponseModel();
		UserEntity authUser = userService.authenticateUser(user.getEmail(), user.getPassword());
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
	@PostMapping("/update-user")
	public ResponseEntity<UserResponseModel> updateUser(@RequestBody UpdateUserModel user){
		UserResponseModel userRespModel = new UserResponseModel();
		UserEntity updatedUser = new UserEntity();
		updatedUser.setFirstName(user.getFirstName()!=null?user.getFirstName():null);
		updatedUser.setLastName(user.getLastName()!=null?user.getLastName():null);
		updatedUser.setId(user.getId());
		updatedUser.setPhoneNumber(user.getPhoneNumber()!=null?user.getPhoneNumber():null);
		
		UserEntity newUser = userService.updateUser(updatedUser,user.getPassword());
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
	@GetMapping("/findAll")
	public ResponseEntity<UserFindAllRespModel> findAll(){
		UserFindAllRespModel userRespModel = new UserFindAllRespModel();
		List<UserEntity> allUsers = userService.findAllUsers();
		if(allUsers!=null) {
			userRespModel.setAllUsers(allUsers);
			userRespModel.setResponseMessage(HttpStatus.OK.name());
			userRespModel.setResponseCode(HttpStatus.OK.value());
			userRespModel.setStatusMessage("All users has been fetched successfully");
		}else {
			userRespModel.setAllUsers(null);
			userRespModel.setResponseMessage(HttpStatus.NO_CONTENT.name());
			userRespModel.setResponseCode(HttpStatus.NO_CONTENT.value());
			userRespModel.setStatusMessage("Error has occured");
		}
		userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);
	}
	@GetMapping("/findById/{Id}")
	public ResponseEntity<UserResponseModel> findById(@PathVariable int Id){
		UserResponseModel userRespModel = new UserResponseModel();
		UserEntity newUser = userService.findById(Id);
		if(newUser != null) {
			userRespModel.setUser(newUser);
			userRespModel.setResponseMessage(HttpStatus.OK.name());
			userRespModel.setResponseCode(HttpStatus.OK.value());
			userRespModel.setStatusMessage("User Data found successfully");
		}else {
			userRespModel.setUser(null);
			userRespModel.setResponseMessage(HttpStatus.NOT_FOUND.name());
			userRespModel.setResponseCode(HttpStatus.NOT_FOUND.value());
			userRespModel.setStatusMessage("Error has occured");
		}
		userRespModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(userRespModel.getResponseCode()).body(userRespModel);
	}
}