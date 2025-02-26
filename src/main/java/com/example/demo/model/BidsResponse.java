package com.example.demo.model;



import java.util.List;

import com.example.demo.entity.BidsEntity;

import lombok.Data;

@Data
public class BidsResponse extends ResponceModel{
	List<BidsEntity> allBids;
}