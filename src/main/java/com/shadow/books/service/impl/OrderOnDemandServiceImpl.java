package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.OrderOnDemand;
import com.shadow.books.domain.User;
import com.shadow.books.repository.OrderOnDemandRepository;
import com.shadow.books.repository.UserRepository;
import com.shadow.books.service.OrderOnDemandService;

@Service
public class OrderOnDemandServiceImpl implements OrderOnDemandService {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	OrderOnDemandRepository orderOnDemandRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public OrderOnDemand addOnDemandOrder(OrderOnDemand orderOnDemand, long userId) {

		Optional<User> userDetail = userRepository.findById(userId);
		logger.info("OPTIONAL USER DETAIL :: " + userDetail);

		if (userDetail.isPresent()) {
			logger.info("USER PRESENT");
			orderOnDemand.setDeleted(false);
			orderOnDemand.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			orderOnDemand.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			orderOnDemand.setName(userDetail.get().getName());
			orderOnDemand.setContactNumber(userDetail.get().getContactNo());
			return orderOnDemandRepository.save(orderOnDemand);
		}
		logger.info("USER NOT PRESENT");
		return null;
	}

}
