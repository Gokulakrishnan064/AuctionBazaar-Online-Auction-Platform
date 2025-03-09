package com.auction.app.model;


import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class UpdateAuctionModel {
	private int id;
	private int user_id;
	private int categoryId;
	private String title;
	private String description;
	private String startDateTime;
	private String endDateTime;
	private String basePrice;
	private String currentPrice;
	private MultipartFile image;
}
