package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;
@Data
public class ResponceModel {
	private String statusMessage;
	private int responseCode;
	private String responseMessage;
	private Timestamp timeStamp;
}
