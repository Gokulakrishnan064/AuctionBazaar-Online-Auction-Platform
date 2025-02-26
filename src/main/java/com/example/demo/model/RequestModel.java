package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RequestModel {
	private int user_id;
	private Timestamp timesStamp;
}
