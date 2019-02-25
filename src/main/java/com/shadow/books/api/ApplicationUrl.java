package com.shadow.books.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.dto.ApplicationDetailsDto;
import com.shadow.books.service.UserService;

@RestController
@RequestMapping("share")
public class ApplicationUrl {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@GetMapping()
	private ResponseEntity<ApplicationDetailsDto> getApplicationUrl() {

		logger.info("Get APPLICATION URL");
		ApplicationDetailsDto applicationDetails = new ApplicationDetailsDto();
		applicationDetails.setBody("I'm inviting you to use ShadowBooks, an online book store app.");
		applicationDetails.setLink("https://www.youtube.com/watch?v=P9Z6okLJDv0");
		return new ResponseEntity<ApplicationDetailsDto>(applicationDetails, HttpStatus.OK);

	}
}
