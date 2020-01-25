package com.manas.app.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
	BookingResponseHeader header;
	BookingResponseBody body;
	public BookingResponseHeader getHeader() {
		return header;
	}
	public BookingResponseBody getBody() {
		return body;
	}
}
@JsonIgnoreProperties(ignoreUnknown = true)
class BookingResponseHeader{
	String title;
	String subTitle;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
}
@JsonIgnoreProperties(ignoreUnknown = true)
class BookingResponseBody{
	String date;
	String activityType;
	String subActivityType;
	String activityName;
	String title;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getSubActivityType() {
		return subActivityType;
	}
	public void setSubActivityType(String subActivityType) {
		this.subActivityType = subActivityType;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
}
