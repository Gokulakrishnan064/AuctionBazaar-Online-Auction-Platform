package com.auction.app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.auction.app.config.FileUploadProperties;
import com.auction.app.entity.AuctionEntity;
import com.auction.app.model.AddAuctionModel;
import com.auction.app.model.AuctionResponseModel;
import com.auction.app.model.FindAllAuctionRespModel;
import com.auction.app.model.UpdateAuctionModel;
import com.auction.app.service.impl.AuctionServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auction")
public class AuctionController {
	
	@Autowired
	private AuctionServiceImpl auctionServiceImpl;
	
	@Autowired
	private FileUploadProperties fileUploadProperties;
	
	@PostMapping(value = "/save-auction", consumes = "multipart/form-data")
	public ResponseEntity<AuctionResponseModel> saveAuction(@ModelAttribute AddAuctionModel auction) throws IOException{
		AuctionEntity newAuction = new AuctionEntity();
		newAuction.setUser_id(auction.getUser_id());
		newAuction.setTitle(auction.getTitle());
		newAuction.setBasePrice(auction.getBasePrice());
		newAuction.setStartDateTime(Timestamp.valueOf(auction.getStartDateTime()));
		newAuction.setCategoryId(auction.getCategoryId());
		newAuction.setEndDateTime(Timestamp.valueOf(auction.getEndDateTime()));
		newAuction.setDescription(auction.getDescription());
		newAuction.setCurrentPrice("0");
		if(auction.getImage()!=null && !auction.getImage().isEmpty()) {
			String imageFileName = generateUniqueFileName(auction.getImage().getOriginalFilename());
			Path path = Paths.get(fileUploadProperties.getUploadDir(), imageFileName);
			Files.write(path, auction.getImage().getBytes());
			newAuction.setImagePath(imageFileName);
		}
		else {
			newAuction.setImagePath(null);
		}
		
		AuctionResponseModel response = new AuctionResponseModel();
		
		AuctionEntity savedAuction = auctionServiceImpl.saveAuction(newAuction);
		if(savedAuction!=null) {
			response.setAuction(savedAuction);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction Saved Successfully");
		}else {
			response.setAuction(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	
	@GetMapping("/deleteAuction/{id}")
	public ResponseEntity<AuctionResponseModel> deleteAuction(@PathVariable int id) throws IOException{
		AuctionResponseModel response = new AuctionResponseModel();
		AuctionEntity newAuction = auctionServiceImpl.deleteAuction(id);
		if(newAuction!=null) {
			response.setAuction(newAuction);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction Saved Successfully");
		}else {
			response.setAuction(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	@GetMapping("/findById/{id}")
	public ResponseEntity<AuctionResponseModel> findAuctionById(@PathVariable int id){
		AuctionResponseModel response = new AuctionResponseModel();
		AuctionEntity newAuction = auctionServiceImpl.getAuctionById(id);
		if(newAuction!=null) {
			response.setAuction(newAuction);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction fetched Successfully");
		}else {
			response.setAuction(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	@PutMapping(value = "/update-auction", consumes = "multipart/form-data")
	public ResponseEntity<AuctionResponseModel> updateAuction(@ModelAttribute UpdateAuctionModel auction) throws IOException{
		AuctionEntity newAuction = new AuctionEntity();
		newAuction.setId(auction.getId());
		newAuction.setUser_id(auction.getUser_id());
		newAuction.setCategoryId(auction.getCategoryId());
		newAuction.setTitle((auction.getTitle()!=null && !auction.getTitle().equals(""))?auction.getTitle():null);
		newAuction.setBasePrice((auction.getBasePrice()!=null && !auction.getBasePrice().equals(""))?auction.getBasePrice():null);
		newAuction.setStartDateTime((auction.getStartDateTime()!=null && !auction.getStartDateTime().equals(""))?Timestamp.valueOf(auction.getStartDateTime()):null);		
		newAuction.setEndDateTime((auction.getEndDateTime()!=null && !auction.getEndDateTime().equals(""))?Timestamp.valueOf(auction.getEndDateTime()):null);
		newAuction.setDescription((auction.getDescription()!=null && !auction.getDescription().equals(""))?auction.getDescription():null);
		newAuction.setCurrentPrice((auction.getCurrentPrice())!=null && auction.getCurrentPrice().equals("")?auction.getCurrentPrice():null);
		if(auction.getImage()!=null && !auction.getImage().isEmpty()) {
			String imageFileName = generateUniqueFileName(auction.getImage().getOriginalFilename());
			Path path = Paths.get(fileUploadProperties.getUploadDir(), imageFileName);
			Files.write(path, auction.getImage().getBytes());
			newAuction.setImagePath(imageFileName);
		}
		else {
			newAuction.setImagePath(null);
		}
		
		AuctionResponseModel response = new AuctionResponseModel();
		
		AuctionEntity savedAuction = auctionServiceImpl.updateAuction(newAuction);
		if(savedAuction!=null) {
			response.setAuction(savedAuction);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction Updated Successfully");
		}else {
			response.setAuction(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	@GetMapping("/findAllAuction")
	public ResponseEntity<FindAllAuctionRespModel> findAllAuction(){
		FindAllAuctionRespModel response = new FindAllAuctionRespModel();
		List<AuctionEntity> auctions = auctionServiceImpl.findAll();
		if(auctions!=null) {
			response.setAuctions(auctions);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction fetched Successfully");
		}else {
			response.setAuctions(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	@GetMapping("/findAuctionByUserId/{userId}")
	public ResponseEntity<FindAllAuctionRespModel> findAllAuction(@PathVariable int userId){
		FindAllAuctionRespModel response = new FindAllAuctionRespModel();
		List<AuctionEntity> auctions = auctionServiceImpl.getAuctionByUserId(userId);
		if(auctions!=null) {
			response.setAuctions(auctions);
			response.setResponseCode(HttpStatus.OK.value());
			response.setResponseMessage(HttpStatus.OK.name());
			response.setStatusMessage("Auction fetched Successfully");
		}else {
			response.setAuctions(null);
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseMessage(HttpStatus.BAD_REQUEST.name());
			response.setStatusMessage("Something Went Wrong");
		}
		response.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
		return ResponseEntity.status(response.getResponseCode()).body(response);
	}
	
	
	private String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + fileExtension;
    }
}
