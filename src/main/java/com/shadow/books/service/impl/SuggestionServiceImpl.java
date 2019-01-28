package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Suggestion;
import com.shadow.books.domain.User;
import com.shadow.books.repository.SuggestionRepository;
import com.shadow.books.repository.UserRepository;
import com.shadow.books.service.SuggestionService;

@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	SuggestionRepository suggestionRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Suggestion add(Suggestion suggestion, long userId) {

		Optional<User> userDetails = userRepository.findById(userId);

		if (userDetails.isPresent()) {
			suggestion.setDeleted(false);
			suggestion.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			suggestion.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			suggestion.setName(userDetails.get().getName());
			suggestion.setContactNumber(userDetails.get().getContactNo());
			return suggestionRepository.save(suggestion);
		}
		return null;
	}
}
