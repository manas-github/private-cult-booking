package com.manas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

	@Autowired
	BookingService bookingService;
	

	@Scheduled(cron="0 23 23 * * *",zone = "GMT+5:30")
	public void book() {
		bookingService.book();
	}
	@Scheduled(cron="0 0 8 * * *",zone = "GMT+5:30")
	public void book() {
		System.out.println("check log");
	}
}
