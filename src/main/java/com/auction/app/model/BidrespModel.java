package com.auction.app.model;

import com.auction.app.entity.BidsEntity;

import lombok.Data;

@Data
public class BidrespModel extends ResponseModel {
	private BidsEntity bid;
}
