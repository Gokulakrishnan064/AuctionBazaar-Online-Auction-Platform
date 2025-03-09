package com.auction.app.model;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class AddAuctionModel {
	private int user_id;
	private String title;
	private String description;
	private String startDateTime;
	private String endDateTime;
	private String basePrice;
	private int categoryId;
	private MultipartFile image; 
}
