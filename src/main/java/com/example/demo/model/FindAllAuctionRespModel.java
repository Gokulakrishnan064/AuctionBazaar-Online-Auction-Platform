package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.AuctionEntity;

import lombok.Data;

@Data
public class FindAllAuctionRespModel extends ResponceModel{
	private List<AuctionEntity> auctions;

}