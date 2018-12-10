package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

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

	@Override
	public User add(User user) {

		Optional<User> optUser = userRepository.findByContactNo(user.getContactNo());
		if (optUser.isPresent()) {
			if(optUser.get().isDeleted()) {
				optUser.get().setDeleted(false);
				update(optUser.get());
			}
			return optUser.get();
		}
		
		user.setDeleted(false);
		user.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		user.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		user.setDeleted(false);
		user.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
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
		if (optUser.isPresent()) {
			userRepository.deleteById(id);
		}
	}
}
