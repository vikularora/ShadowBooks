package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Suggestion;
import com.shadow.books.repository.SuggestionRepository;
import com.shadow.books.service.SuggestionService;

@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	SuggestionRepository suggestionRepository;

	@Override
	public Suggestion add(Suggestion suggestion) {

		suggestion.setDeleted(false);
		suggestion.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		suggestion.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return suggestionRepository.save(suggestion);
	}

}
