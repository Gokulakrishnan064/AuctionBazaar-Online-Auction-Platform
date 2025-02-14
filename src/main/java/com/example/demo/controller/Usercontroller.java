package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Userentity;
import com.example.demo.serice.impl.Userserviseimplementation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auction")

public class Usercontroller {
	@Autowired
	private Userserviseimplementation userImpl;
	@PostMapping("/saveuser")
	public ResponseEntity<?> saveUsers(@RequestBody Userentity user){
	Userentity newUser=userImpl.saveUser(user);
	if(newUser!=null) {
		return ResponseEntity.status(HttpStatus.OK).body(newUser);
	}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("user with email is already present");
		
	}	
@GetMapping("/deleteuser")
public String deletebyid(@RequestParam("id") int id ) {
    return userImpl.delectbyId(id);
}
@PostMapping("/updateuser")
public ResponseEntity<?> updateUsers(@RequestBody Userentity user){
	Userentity newUser=userImpl.updateUser(user);
	if(newUser!=null) {
		return ResponseEntity.status(HttpStatus.OK).body(newUser);
	}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("user with email is already present");
		
	}
@PostMapping("/authandicate")
public ResponseEntity<?> authandicateUsers(@RequestParam ("email")String email, @RequestParam("password")String password ){
	Userentity newUser=userImpl.authandicateUser(email,password);
	if(newUser!=null) {
		return ResponseEntity.status(HttpStatus.OK).body(newUser);
	}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("user with email is already present");
		
	}
}

