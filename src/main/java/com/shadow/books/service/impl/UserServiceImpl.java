package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.User;
import com.shadow.books.repository.UserRepository;
import com.shadow.books.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public User add(User user) {

		long time = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
		Optional<User> optUser = userRepository.findByContactNo(user.getContactNo());
		logger.info("GET OPTIONAL USER BY CONTACT NUMBER :: " + optUser);

		if (optUser.isPresent()) {
			logger.info("USER ALREADY EXISTS ");

			if (optUser.get().isDeleted()) {
				logger.info("UPDATE USER DELETED STATUS");
				update(optUser.get());
			}

			optUser.get().setName(user.getName());
			return update(optUser.get());
		}
		logger.info("HURREY!! NEW USER JOINED US");
		user.setDeleted(false);
		user.setCreatedOn(time);
		user.setModifiedOn(time);
		logger.info("BEFORE JOINING NEW USER :: " + user);
		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		user.setDeleted(false);
		user.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		logger.info("BEFORE UPDATE USER :: " + user);
		return userRepository.save(user);
	}

	@Override
	public Page<User> list(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<User> userList = userRepository.findAll(pageable);
		return userList;
	}

	@Override
	public Optional<User> finById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		Optional<User> optUser = finById(id);
		logger.info("DELETE USER" + optUser);
		if (optUser.isPresent()) {
			logger.info("USER PRESENT");
			userRepository.deleteById(id);
		}
	}
}
