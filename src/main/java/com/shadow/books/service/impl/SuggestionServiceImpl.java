package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Suggestion;
import com.shadow.books.domain.User;
import com.shadow.books.repository.SuggestionRepository;
import com.shadow.books.repository.UserRepository;
import com.shadow.books.service.SuggestionService;

@Service
public class SuggestionServiceImpl implements SuggestionService {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	SuggestionRepository suggestionRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Suggestion add(Suggestion suggestion, long userId) {

		Optional<User> userDetail = userRepository.findById(userId);
		logger.info("OPTIONAL USER DETAIL :: " + userDetail);

		if (userDetail.isPresent()) {
			logger.info("USER PRESENT");
			suggestion.setDeleted(false);
			suggestion.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			suggestion.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			suggestion.setName(userDetail.get().getName());
			suggestion.setContactNumber(userDetail.get().getContactNo());
			logger.info("BEFORE SUGGESTION SAVED");
			return suggestionRepository.save(suggestion);
		}
		logger.info("USER NOT PRESENT");
		return null;
	}
}
