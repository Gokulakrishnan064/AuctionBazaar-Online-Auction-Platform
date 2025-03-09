package com.auction.app.model;

import lombok.Data;

@Data
public class SaveBidReqModel {
	private int userId;
	private int auctionId;
	private String amount;
}
