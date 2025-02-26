package com.example.demo.controller;

import com.example.demo.entity.BidsEntity;
import com.example.demo.model.BidrespModel;
import com.example.demo.model.BidsResponse;
import com.example.demo.model.SaveBidReqModel;
import com.example.demo.serice.impl.BidsServiceImpl;
import com.example.demo.serice.impl.Userserviseimplementation;

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
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bids")
public class BidsController {
	
	@Autowired
	private BidsServiceImpl bidsService;
	
	@Autowired
	private Userserviseimplementation userService;
	
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
	@PostMapping("/SaveBids")
	public ResponseEntity<BidrespModel> saveBid(@RequestBody SaveBidReqModel bid){
		BidrespModel respModel = new BidrespModel();
		BidsEntity saveBid = new BidsEntity();
		saveBid.setUserId(bid.getUserId());
		saveBid.setAuctionId(bid.getAuctionId());
		saveBid.setAmount(bid.getAmount());
		saveBid.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		saveBid.setCreatedBy(userService.findById(bid.getUserId()).getFirstname());
		BidsEntity response = bidsService.saveRepo(saveBid);
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
	@GetMapping("/getByLargestBid")
	public ResponseEntity<BidrespModel> getLargeBids(){
		BidrespModel respModel = new BidrespModel();
		BidsEntity response = bidsService.readRecentHighestBid();
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