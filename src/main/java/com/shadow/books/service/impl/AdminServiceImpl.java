package com.shadow.books.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Admin;
import com.shadow.books.repository.AdminRepository;
import com.shadow.books.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository;

	@Override
	public Admin add(String mobileNumber, String password) {

		Admin admin = adminRepository.findByMobileNumber(mobileNumber);
		if (admin != null && admin.getPassword().equalsIgnoreCase(password)) {
			return admin;
		}
		return null;
	}
}
