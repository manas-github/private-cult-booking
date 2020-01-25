package com.manas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

	@Autowired
	BookingService bookingService;
	

	@Scheduled(fixedRate=1000000000)
	public void book() {
		bookingService.book();
	}
}
