package com.auction.app.model;

import com.auction.app.entity.AuctionEntity;

import lombok.Data;

@Data
public class AuctionResponseModel extends ResponseModel{
	private AuctionEntity auction;
}
