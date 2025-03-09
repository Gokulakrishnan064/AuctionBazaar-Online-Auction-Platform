package com.auction.app.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auction.app.entity.UserCredentialsEntity;
import com.auction.app.entity.UserEntity;
import com.auction.app.repository.UserCredentialsRepository;
import com.auction.app.repository.UserRepository;
@Service
public class UserServiceImplementation {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserCredentialsRepository userCredRepo;
	
	@Autowired
	private MailServiceImpl mailService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	
	
	public UserEntity saveUser(UserEntity user,String password) {
		if(userRepo.findByEmail(user.getEmail())==null) {
			user.setPhoneNumber(user.getPhoneNumber()!=null?user.getPhoneNumber():null);
			user.setCreatedBy(user.getFirstName()+" "+user.getLastName());
			user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			user.setUpdatedAt(null);
			user.setUpdatedBy(null);
			UserEntity savedUser = userRepo.save(user);
			saveUserCred(savedUser.getEmail(), password, savedUser.getId());
			mailService.sendWelcomeEmail(user.getEmail(),user.getUserName());
			return savedUser;
		}
		return null;
	}
	public void saveUserCred(String email,String password,int id) {
		UserCredentialsEntity userCred = new UserCredentialsEntity();
		userCred.setUser_Id(id);
		userCred.setEmail(email);
		userCred.setPassword(encoder.encode(password));
		userCredRepo.save(userCred);
	}
	public UserEntity findByEmail(String email) {
		UserEntity user = userRepo.findByEmail(email);
		return user==null?null:user;
	}
	public UserEntity deleteUser(int Id) {
		Optional<UserEntity> user = userRepo.findById(Id);
		userRepo.deleteById(Id);
		UserCredentialsEntity userAuth=userCredRepo.findByuserId(Id);
		userCredRepo.deleteById(userAuth.getId());
		return user.isPresent()?user.get():null;
	}
	public UserEntity authenticateUser(String email,String password) {
		UserCredentialsEntity user= userCredRepo.findByEmail(email);
		if(user!=null) {
			if(encoder.matches(password, user.getPassword())) {
				return findByEmail(user.getEmail());
			}
		}
		return null;
	}
	public UserEntity updateUser(UserEntity user,String password) {
		Optional<UserEntity> oldUser = userRepo.findById(user.getId());
		if(oldUser.isPresent()) {
			UserEntity oldUserData = oldUser.get();
			UserEntity newUserData = user;
			newUserData.setUserName(oldUserData.getUserName());
			newUserData.setId(user.getId());
			newUserData.setEmail(oldUserData.getEmail());
			newUserData.setFirstName(newUserData.getFirstName()==null?oldUserData.getFirstName():newUserData.getFirstName());
			newUserData.setLastName(newUserData.getLastName()==null?oldUserData.getLastName():newUserData.getLastName());
			newUserData.setPhoneNumber(newUserData.getPhoneNumber()==null?oldUserData.getPhoneNumber():newUserData.getPhoneNumber());
			newUserData.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
			newUserData.setUpdatedBy((newUserData.getFirstName()==null?oldUserData.getFirstName():newUserData.getFirstName())+" "+(newUserData.getLastName()==null?oldUserData.getLastName():newUserData.getLastName()));
			newUserData.setCreatedAt(oldUserData.getCreatedAt());
			newUserData.setCreatedBy(oldUserData.getCreatedBy());
			if(password!=null) {
				UserCredentialsEntity oldUserCred = userCredRepo.findByuserId(user.getId());
				UserCredentialsEntity newUserCred = new UserCredentialsEntity();
				newUserCred.setId(oldUserCred.getId());
				newUserCred.setEmail(oldUserCred.getEmail());
				newUserCred.setPassword(encoder.encode(password));
				newUserCred.setUser_Id(oldUserCred.getUser_Id());
				userCredRepo.save(newUserCred);
			}
			return userRepo.save(newUserData);	
		}
		else {
			return null;
		}
	}
	public List<UserEntity> findAllUsers(){
		return userRepo.findAll();
	}
	public UserEntity findById(int id) {
		Optional<UserEntity> res = userRepo.findById(id);
		if(res.isPresent()) {
			return res.get();
		}else {
			return null;
		}
	}
}

