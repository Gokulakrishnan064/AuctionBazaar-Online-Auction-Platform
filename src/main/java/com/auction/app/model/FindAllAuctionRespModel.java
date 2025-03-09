package com.auction.app.model;

import java.util.List;

import com.auction.app.entity.AuctionEntity;

import lombok.Data;

@Data
public class FindAllAuctionRespModel extends ResponseModel{
	private List<AuctionEntity> auctions;

}
