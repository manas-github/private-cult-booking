
package com.manas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/api/hello")
@RestController
public class HelloController {
	

	@RequestMapping(value="",method = RequestMethod.GET)
	public ResponseEntity<String> getUser(){
			return new ResponseEntity<>("Hello from cult script",HttpStatus.OK);
	}
	
}

