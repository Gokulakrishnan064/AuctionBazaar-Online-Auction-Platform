package com.auction.app.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResponseModel {
	
	private String statusMessage;
	private int responseCode;
	private String responseMessage;
	private Timestamp timeStamp;
}
