package com.auction.app.model;

import java.util.List;

import com.auction.app.entity.BidsEntity;

import lombok.Data;

@Data
public class BidsResponse extends ResponseModel{
	List<BidsEntity> allBids;
}
