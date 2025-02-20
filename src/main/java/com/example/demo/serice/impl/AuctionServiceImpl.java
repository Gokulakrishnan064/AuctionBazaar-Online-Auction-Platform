package com.example.demo.serice.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AuctionEntity;
import com.example.demo.entity.Userentity;
import com.example.demo.repository.AuctionRepo;
import com.example.demo.repository.Userrepo;


@Service
public class AuctionServiceImpl {

	@Autowired
	private AuctionRepo auctionRepo;
	
	@Autowired
	private Userrepo userRepo;
	
	public AuctionEntity saveAuction(AuctionEntity auction) {
		Optional<Userentity> user = userRepo.findById(auction.getUser_id());
		if(!user.isEmpty()) {
			Userentity user1 = user.get();
			auction.setCreatedBy(user1.getFirstname()+" "+user1.getLastname());
			auction.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			return auctionRepo.save(auction);
		}
		return null;
	}
	public AuctionEntity deleteAuction(int id) {
		Optional<AuctionEntity> auction = auctionRepo.findById(id);
		if(!auction.isEmpty()) {
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
	public AuctionEntity updateAuction(AuctionEntity auction) {
		AuctionEntity newAuction = new AuctionEntity();
		Optional<AuctionEntity> oldAuction = auctionRepo.findById(auction.getId());
		if(!oldAuction.isEmpty()) {
			AuctionEntity old = oldAuction.get();
			newAuction.setId(old.getId());;
			newAuction.setTitle(auction.getTitle()!=null?auction.getTitle():old.getTitle());
			newAuction.setUser_id(old.getUser_id());
			newAuction.setDescription(auction.getDescription()!=null?auction.getDescription():old.getDescription());
			newAuction.setImagePath(auction.getImagePath()!=null?auction.getImagePath():old.getImagePath());
			newAuction.setBasePrice(auction.getBasePrice()!=null?auction.getBasePrice():old.getBasePrice());
			newAuction.setStartDate(auction.getStartDate()!=null?auction.getStartDate():old.getStartDate());
			newAuction.setStartTime(auction.getStartTime()!=null?auction.getStartTime():old.getStartTime());
			newAuction.setEndDate(auction.getEndDate()!=null?auction.getEndDate():old.getEndDate());
			newAuction.setEndTime(auction.getEndTime()!=null?auction.getEndTime():old.getEndTime());
			newAuction.setCreatedAt(old.getCreatedAt());
			newAuction.setCreatedBy(old.getCreatedBy());
			newAuction.setUpdatedBy(old.getCreatedBy());
			newAuction.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
			return auctionRepo.save(newAuction);
		}
		return null;
	}   

}