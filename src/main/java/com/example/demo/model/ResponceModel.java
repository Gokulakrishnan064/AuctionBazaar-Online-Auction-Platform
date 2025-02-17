package com.example.demo.model;

import java.sql.Timestamp;

public class ResponceModel {
	private String statusMessage;
	private int responseCode;
	private String responseMessage;
	private Timestamp timeStamp;
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "ResponceModel [statusMessage=" + statusMessage + ", responseCode=" + responseCode + ", responseMessage="
				+ responseMessage + ", timeStamp=" + timeStamp + "]";
	}
	
}
