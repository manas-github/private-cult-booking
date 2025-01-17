/**
 * 
 */
package com.manas.app.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manas.app.dao.Classes;
import com.manas.app.dao.ClassesDao;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * @author manassingh
 *
 */
@Service
public class BookingService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private JavaMailSender sender;

	@Value("${cult.bookingWorkoutName}")
	String bookingWorkoutName;
	
	@Value("${cult.centreID}")
	String centreID;

	@Value("${cult.mobile}")
	String mobile;;
	
	@Value("${cult.email}")
	String email;;
	
	@Value("${cult.apiKey}")
	String apiKey;;
	
	@Value("${cult.cookie}")
	String cookie;
	
	@Value("${cult.bookingApi}")
	String bookingApi;
	
	@Value("${cult.classListApi}")
	String classListApi;
	
	@Value("${cult.sundayTime}")
	String sundayTime;
	
	@Value("${cult.mondayTime}")
	String mondayTime;
	
	@Value("${cult.tuesdayTime}")
	String tuesdayTime;
	
	@Value("${cult.wednesdayTime}")
	String wednesdayTime;
	
	@Value("${cult.thursdayTime}")
	String thursdayTime;
	
	@Value("${cult.fridayTime}")
	String fridayTime;
	
	@Value("${cult.saturdayTime}")
	String saturdayTime;

	public void book() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("apikey", apiKey);
		headers.add("cookie", cookie);
		HttpEntity entity = new HttpEntity(headers);
		String classeslisturl = classListApi.replace("centreid", centreID);
		ResponseEntity<String> response = restTemplate.exchange(classeslisturl, HttpMethod.GET, entity, String.class);
		String result = response.getBody().toString();
		ClassesDao res = new ClassesDao();
		try {
			res = new ObjectMapper().readValue(response.getBody().toString(), ClassesDao.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> workoutsToBook = Arrays.asList(bookingWorkoutName.split(","));
		List<Classes> filteredClasses = new ArrayList<Classes>();
		res.getClassByDateList().forEach(classbyDate -> {
			classbyDate.getClassByTimeList().forEach(classByTime -> {
				classByTime.getClasses().forEach(classes -> {
					if (workoutsToBook.contains(classes.getWorkoutName())) {
						filteredClasses.add(classes);
					}
				});
			});
		});
		
		System.out.println("Filtered classes");
		filteredClasses.forEach(classes->{
			System.out.println(classes.toString());
		});

		HashMap<String,List<String>> hm = new HashMap<>();
		hm.put(DayOfWeek.MONDAY.name(), Arrays.asList(mondayTime.split(",")));
		hm.put(DayOfWeek.TUESDAY.name(), Arrays.asList(tuesdayTime.split(",")));
		hm.put(DayOfWeek.WEDNESDAY.name(),Arrays.asList(wednesdayTime.split(",")) );
		hm.put(DayOfWeek.THURSDAY.name(),Arrays.asList(thursdayTime.split(",")));
		hm.put(DayOfWeek.FRIDAY.name(), Arrays.asList(fridayTime.split(",")));
		hm.put(DayOfWeek.SATURDAY.name(),Arrays.asList(saturdayTime.split(",")));
		hm.put(DayOfWeek.SUNDAY.name(),Arrays.asList(sundayTime.split(",")) );

		List<Classes> toBook = new ArrayList<>();
		for(int i=0;i<filteredClasses.size();i++) {
			if (Integer.parseInt(filteredClasses.get(i).getAvailableSeats()) > 0 
					&&  filteredClasses.get(i).getState().equals("AVAILABLE")) {
				if(hm.get(convertDateToDay(filteredClasses.get(i).getDate())).contains(filteredClasses.get(i).getStartTime())) {
					toBook.add(filteredClasses.get(i));
				}
				
			}
		}
		System.out.println("ToBook classes");
		toBook.forEach(classes->{
			System.out.println(classes.toString());
		});
		
		if (toBook.size() > 7) {
			bookNow(toBook.subList(0, 6));
			System.out.println("more than 7 classes found");
		} else {
			bookNow(toBook);
		}

	}

	public void bookNow(List<Classes> classes) {
		List<BookingResponse> bookingResponses = new ArrayList<>();
		classes.forEach(classtoBook -> {
			try {
				String bookingApiWithId = bookingApi.replace("classid", classtoBook.getId());
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.add("apikey", apiKey);
				headers.add("cookie", cookie);
				HttpEntity entity = new HttpEntity(headers);
				System.out.println(bookingApi);
				ResponseEntity<String> response = restTemplate.exchange(bookingApiWithId, HttpMethod.POST, entity, String.class);
				String result = response.getBody().toString();
				System.out.println(result);
				try {
					BookingResponse res = new ObjectMapper().readValue(response.getBody().toString(),
							BookingResponse.class);
					bookingResponses.add(res);
					System.out.println(res.toString());
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		});
		sendConfirmationMailAndSms(bookingResponses);
	}

	private void sendConfirmationMailAndSms(List<BookingResponse> bookingResponses) {

		String message = "Total booked class : " + bookingResponses.size() + ".";
		sendSMS(message);
		List<String> confMsg = new ArrayList<>();
		bookingResponses.forEach(res -> {
			confMsg.add(res.getHeader().getTitle() + ". " + res.getHeader().getSubTitle());
		});
		confMsg.forEach(msg -> {
			sendSMS(msg);
		});

		String emailBody = "";
		for (int i = 0; i < confMsg.size(); i++) {
			emailBody += "<p>" + confMsg.get(i) + ".</p>";
		}
		try {
			sendEMAIL(emailBody, message);	
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Completed");

	}

	private void sendEMAIL(String emailBody, String message) {
		MimeMessage emailMessage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(emailMessage);

		try {
			helper.setTo(email);
			helper.setText(emailBody, true);
			helper.setSubject("Cult Script: " + message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sender.send(emailMessage);
	}

	private void sendSMS(String msg) {
		// TODO Auto-generated method stub
		

	}
	
	
	public String convertDateToDay(String date) {
        return LocalDateTime.of(Integer.parseInt(date.substring(0,4)),Integer.parseInt(date.substring(5,7)),Integer.parseInt(date.substring(8)),0,0,0).getDayOfWeek().name();
	}

}
