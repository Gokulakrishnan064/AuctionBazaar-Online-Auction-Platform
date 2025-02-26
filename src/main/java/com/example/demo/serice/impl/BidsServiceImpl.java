package com.example.demo.serice.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BidsEntity;
import com.example.demo.repository.BidsRepo;


@Service
public class BidsServiceImpl {
	@Autowired
	private BidsRepo bidsRepo;
	
	public BidsEntity saveRepo(BidsEntity bids) {
		return bidsRepo.save(bids);
	}
	
	public BidsEntity readRecentHighestBid() {
		return bidsRepo.findHighestBid()!=null?bidsRepo.findHighestBid():null;
	}
	public List<BidsEntity> findBidsByUserId(int userID) {
		return bidsRepo.findByUserId(userID);
	}
	public BidsEntity findById(int Id) {
		Optional<BidsEntity> res = bidsRepo.findById(Id);
		return res.isEmpty()?null:res.get();
	}
	
}
