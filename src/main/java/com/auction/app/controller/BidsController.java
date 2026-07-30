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

import com.auction.app.entity.BidsEntity;
import com.auction.app.model.BidrespModel;
import com.auction.app.model.BidsResponse;
import com.auction.app.model.SaveBidReqModel;
import com.auction.app.service.impl.BidsServiceImpl;
import com.auction.app.service.impl.UserServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bids")
public class BidsController {
	
	@Autowired
	private BidsServiceImpl bidsService;
	
	@Autowired
	private UserServiceImplementation userService;
	
	@GetMapping("/getBidsByUserId/{id}")
	public ResponseEntity<BidsResponse> getBidsByUserId(@PathVariable int id){
		BidsResponse respModel = new BidsResponse();
		List<BidsEntity> response = bidsService.findBidsByUserId(id);
		if(response!=null) {
			respModel.setStatusMessage("Sucessfully fetched");
			respModel.setAllBids(response);
			respModel.setResponseCode(HttpStatus.OK.value());
			respModel.setResponseMessage(HttpStatus.OK.name());
		}else {
			respModel.setStatusMessage("Data Unavailable");
			respModel.setAllBids(response);
			respModel.setResponseCode(HttpStatus.CONFLICT.value());
			respModel.setResponseMessage(HttpStatus.CONFLICT.name());
		}
		respModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(respModel.getResponseCode()).body(respModel);
	}

	@GetMapping("/getAllBidsByAuctionId/{auctionId}")
	public ResponseEntity<BidsResponse> getAllBidsByAuctionId(@PathVariable int auctionId){
		BidsResponse respModel = new BidsResponse();
		List<BidsEntity> response = bidsService.findAllBidsByAuctionId(auctionId);
		if(response!=null) {
			respModel.setStatusMessage("Sucessfully fetched");
			respModel.setAllBids(response);
			respModel.setResponseCode(HttpStatus.OK.value());
			respModel.setResponseMessage(HttpStatus.OK.name());
		}else {
			respModel.setStatusMessage("Data Unavailable");
			respModel.setAllBids(response);
			respModel.setResponseCode(HttpStatus.CONFLICT.value());
			respModel.setResponseMessage(HttpStatus.CONFLICT.name());
		}
		respModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(respModel.getResponseCode()).body(respModel);
	}

	@PostMapping("/SaveBids")
	public ResponseEntity<BidrespModel> saveBid(@RequestBody SaveBidReqModel bid){
		BidrespModel respModel = new BidrespModel();
		BidsEntity saveBid = new BidsEntity();
		saveBid.setUserId(bid.getUserId());
		saveBid.setAuctionId(bid.getAuctionId());
		saveBid.setAmount(bid.getAmount());
		saveBid.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		saveBid.setCreatedBy(userService.findById(bid.getUserId()).getUserName());
		BidsEntity response = bidsService.saveRepo(saveBid);
		if(response!=null) {
			respModel.setStatusMessage("Bid placed successfully");
			respModel.setBid(response);
			respModel.setResponseCode(HttpStatus.OK.value());
			respModel.setResponseMessage(HttpStatus.OK.name());
		}else {
			respModel.setStatusMessage("Bidding is closed for this auction or bid amount is invalid");
			respModel.setBid(null);
			respModel.setResponseCode(HttpStatus.BAD_REQUEST.value());
			respModel.setResponseMessage(HttpStatus.BAD_REQUEST.name());
		}
		respModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(respModel.getResponseCode()).body(respModel);
	}

	@GetMapping("/getByLargestBid/{auctionId}")
	public ResponseEntity<BidrespModel> getLargeBids(@PathVariable int auctionId){
		BidrespModel respModel = new BidrespModel();
		BidsEntity response = bidsService.readRecentHighestBid(auctionId);
		if(response!=null) {
			respModel.setStatusMessage("Sucessfully fetched");
			respModel.setBid(response);
			respModel.setResponseCode(HttpStatus.OK.value());
			respModel.setResponseMessage(HttpStatus.OK.name());
		}else {
			respModel.setStatusMessage("Data Unavailable");
			respModel.setBid(response);
			respModel.setResponseCode(HttpStatus.CONFLICT.value());
			respModel.setResponseMessage(HttpStatus.CONFLICT.name());
		}
		respModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(respModel.getResponseCode()).body(respModel);
	}

	@GetMapping("/bidById/{Id}")
	public ResponseEntity<BidrespModel> BidById(@PathVariable int Id){
		BidrespModel respModel = new BidrespModel();
		BidsEntity response = bidsService.findById(Id);
		if(response!=null) {
			respModel.setStatusMessage("Sucessfully fetched");
			respModel.setBid(response);
			respModel.setResponseCode(HttpStatus.OK.value());
			respModel.setResponseMessage(HttpStatus.OK.name());
		}else {
			respModel.setStatusMessage("Data Unavailable");
			respModel.setBid(response);
			respModel.setResponseCode(HttpStatus.CONFLICT.value());
			respModel.setResponseMessage(HttpStatus.CONFLICT.name());
		}
		respModel.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(respModel.getResponseCode()).body(respModel);
	}
}
