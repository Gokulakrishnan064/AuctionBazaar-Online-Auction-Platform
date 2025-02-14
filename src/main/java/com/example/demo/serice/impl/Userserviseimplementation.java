package com.example.demo.serice.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Userentity;
import com.example.demo.repository.Userrepo;

@Service
public class Userserviseimplementation {
	@Autowired
	private Userrepo userRepo;
	
	public Userentity saveUser(Userentity user) {
		if(userRepo.findByEmail(user.getEmailid())==null) {
			user.setPhoneno(user.getPhoneno()!=null?user.getPhoneno():null);
			user.setCreatedby(user.getFirstname()+" "+user.getLastname());
			user.setCreatedat(Timestamp.valueOf(LocalDateTime.now()));
			user.setUpdatedat(null);
			user.setUpdatedby(null);
			Userentity savedUser = userRepo.save(user);
			return savedUser;
		}
		return null;
	}
	public String delectbyId(int id) {
		if(userRepo.findById(id)!=null) {
			userRepo.deleteById(id);	
			return "user has been deleted";
		}
		return "user not found";
		
	}
	public Userentity updateUser(Userentity user) {
		Optional<Userentity> oldUser = userRepo.findById(user.getId());
		if(oldUser.isPresent()) {
			Userentity oldUserData = oldUser.get();
			Userentity newUserData = user;
			newUserData.setId(oldUserData.getId());
			newUserData.setEmailid(oldUserData.getEmailid());
			newUserData.setFirstname(newUserData.getFirstname()==null?oldUserData.getFirstname():newUserData.getFirstname());
			newUserData.setLastname(newUserData.getLastname()==null?oldUserData.getLastname():newUserData.getLastname());
			newUserData.setPhoneno(newUserData.getPhoneno()==null?oldUserData.getPhoneno():newUserData.getPhoneno());
			newUserData.setUpdatedat(Timestamp.valueOf(LocalDateTime.now()));
			newUserData.setUpdatedby((newUserData.getFirstname()==null?oldUserData.getFirstname():newUserData.getFirstname())+" "+(newUserData.getLastname()==null?oldUserData.getLastname():newUserData.getLastname()));
			newUserData.setCreatedat(oldUserData.getCreatedat());
			newUserData.setCreatedby(oldUserData.getCreatedby());
newUserData.setPassword(newUserData.getPassword()!=null?newUserData.getPassword():oldUserData.getPassword());				
			
			
			return userRepo.save(newUserData);	
		}
		else {
			return null;
		}

	}
	public Userentity authandicateUser(String email,String password)
	{
		Userentity user=userRepo.findByEmail(email);
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				return user;
				
						
			}
		}
		return null;
				
	}
}


