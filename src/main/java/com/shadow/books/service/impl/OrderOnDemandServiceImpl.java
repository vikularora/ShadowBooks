package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.OrderOnDemand;
import com.shadow.books.repository.OrderOnDemandRepository;
import com.shadow.books.service.OrderOnDemandService;

@Service
public class OrderOnDemandServiceImpl implements OrderOnDemandService {

	@Autowired
	OrderOnDemandRepository orderOnDemandRepository;

	@Override
	public OrderOnDemand addOnDemandOrder(OrderOnDemand orderOnDemand) {

		orderOnDemand.setDeleted(false);
		orderOnDemand.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		orderOnDemand.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return orderOnDemandRepository.save(orderOnDemand);

	}

}
