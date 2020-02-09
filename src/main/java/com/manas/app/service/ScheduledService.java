package com.manas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

	@Autowired
	BookingService bookingService;
	

	@Scheduled(cron="0 10 40 * * *",zone = "GMT+5:30")
	public void book() {
		bookingService.book();
	}
}
