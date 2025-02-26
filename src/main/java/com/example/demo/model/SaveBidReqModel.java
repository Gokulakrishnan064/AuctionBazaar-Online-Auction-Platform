package com.example.demo.model;

import lombok.Data;

@Data
public class SaveBidReqModel {
	private int userId;
	private int auctionId;
	private long amount;
}
