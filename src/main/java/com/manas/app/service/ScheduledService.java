package com.manas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

	@Autowired
	BookingService bookingService;
	

	@Scheduled(cron="0 6 0 * * *",zone = "GMT+5:30")
	public void book() {
		bookingService.book();
	}
	@Scheduled(cron="0 0 8 * * *",zone = "GMT+5:30")
	public void cronCheck() {
		System.out.println("check log");
	}
}
