package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddAuctionModel {
	private int user_id;
	private String title;
	private String description;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String basePrice;
	private MultipartFile image; 
}