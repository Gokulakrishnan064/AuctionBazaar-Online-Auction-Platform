package com.example.demo.model;

import com.example.demo.entity.BidsEntity;

import lombok.Data;

@Data
public class BidrespModel extends ResponceModel {
	private BidsEntity bid;
}