package com.auction.app.service.impl;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.app.entity.AuctionEntity;
import com.auction.app.entity.UserEntity;
import com.auction.app.repository.AuctionRepo;
import com.auction.app.repository.UserRepository;

@Service
public class AuctionServiceImpl {

	@Autowired
	private AuctionRepo auctionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MailServiceImpl mail;
	
	public AuctionEntity saveAuction(AuctionEntity auction) {
		Optional<UserEntity> user = userRepo.findById(auction.getUser_id());
		if(!user.isEmpty()) {
			UserEntity user1 = user.get();
			auction.setCreatedBy(user1.getFirstName()+" "+user1.getLastName());
			auction.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			mail.sendEmail(user1.getEmail(), "Your auction has been creates sucessfully", "Hello "+user1.getUserName()+" your auction "+auction.getTitle()+" has been created successfully");
			return auctionRepo.save(auction);
		}
		return null;
	}
	public int updateAmount(int id,String price) {
		int resp = auctionRepo.updateCurrentPrice(id, price);
		if(resp != 0) {
			return 1;
		}
		return 0;
	}
	public AuctionEntity deleteAuction(int id) throws IOException {
		Optional<AuctionEntity> auction = auctionRepo.findById(id);
		if(!auction.isEmpty()) {
			if(auction.get().getImagePath() != null) {
				String uploadDir = "D:\\Sabari\\Learning\\Java\\Eclipse\\git\\AuctionBazaar-Online-Auction-Platform-WebService-Development_Feb_2025\\upload";
				String imagePath = auction.get().getImagePath(); // This contains only the filename
				Path filePath = Paths.get(uploadDir, imagePath);

				System.out.println("Deleting file at: " + filePath.toString());
				Files.delete(Paths.get(filePath.toString()));
			}
			auctionRepo.deleteById(id);
			return auction.get();
		}
		return null;
	}
	public List<AuctionEntity> getAuctionByUserId(int user_Id) {
		List<AuctionEntity> auction = auctionRepo.findByUserId(user_Id);
		if(auction!=null) {
			return auction;
		}
		return null;
	}
	public AuctionEntity getAuctionById(int id) {
		Optional<AuctionEntity> auction = auctionRepo.findById(id);
		if(!auction.isEmpty()) {
			return auction.get();
		}
		return null;
	}
	public List<AuctionEntity> findAll(){
		return auctionRepo.findAll();
	}
	public AuctionEntity updateAuction(AuctionEntity auction) throws IOException {
		AuctionEntity newAuction = new AuctionEntity();
		Optional<AuctionEntity> oldAuction = auctionRepo.findById(auction.getId());
		if(!oldAuction.isEmpty()) {
			AuctionEntity old = oldAuction.get();
			newAuction.setId(old.getId());
			newAuction.setCategoryId(auction.getCategoryId()!=-1?auction.getCategoryId():old.getCategoryId());
			newAuction.setTitle(auction.getTitle()!=null?auction.getTitle():old.getTitle());
			newAuction.setUser_id(old.getUser_id());
			newAuction.setDescription(auction.getDescription()!=null?auction.getDescription():old.getDescription());
			if(old.getImagePath()!=null && auction.getImagePath()!=null) {
				Files.delete(Paths.get(old.getImagePath()));
				newAuction.setImagePath(auction.getImagePath());
			}else {
				newAuction.setImagePath(old.getImagePath());
			}
			newAuction.setBasePrice(auction.getBasePrice()!=null?auction.getBasePrice():old.getBasePrice());
			newAuction.setStartDateTime(auction.getStartDateTime()!=null?auction.getStartDateTime():old.getStartDateTime());
			newAuction.setEndDateTime(auction.getEndDateTime()!=null?auction.getEndDateTime():old.getEndDateTime());
			newAuction.setCreatedAt(old.getCreatedAt());
			newAuction.setCreatedBy(old.getCreatedBy());
			newAuction.setUpdatedBy(old.getCreatedBy());
			newAuction.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
			return auctionRepo.save(newAuction);
		}
		return null;
	}   

}
