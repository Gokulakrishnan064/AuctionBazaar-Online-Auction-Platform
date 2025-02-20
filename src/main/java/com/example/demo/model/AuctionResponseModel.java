package com.example.demo.model;


import com.example.demo.entity.AuctionEntity;

import lombok.Data;

@Data
public class AuctionResponseModel extends ResponceModel{
	private AuctionEntity auction;
}
