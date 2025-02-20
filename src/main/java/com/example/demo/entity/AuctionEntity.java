package com.example.demo.entity;

import java.sql.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="auction_data")
public class AuctionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int user_id;
	private String title;
	private String description;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String basePrice;
	private String imagePath;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
}