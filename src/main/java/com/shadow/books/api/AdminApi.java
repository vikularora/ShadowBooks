package com.shadow.books.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.Admin;
import com.shadow.books.service.AdminService;

@RestController
@RequestMapping("/admin/")
public class AdminApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	AdminService adminService;

	@CrossOrigin
	@GetMapping("login")
	public ResponseEntity<Admin> getAdminDetails(
			@RequestParam(required = true, name = "mobileNumber") String mobileNumber,
			@RequestParam(required = true, name = "password") String password) throws Exception {

		Admin adminDetails = adminService.add(mobileNumber, password);

		if (adminDetails != null) {
			logger.info("Admin IS Present :: " + adminDetails);
			return new ResponseEntity<Admin>(adminDetails, HttpStatus.CREATED);
		}
		logger.info("NULLA Admin :: " + adminDetails);
		return new ResponseEntity<Admin>(adminDetails, HttpStatus.NO_CONTENT);
	}

}
