package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Orders;
import com.shadow.books.repository.LineItemRepository;
import com.shadow.books.repository.OrderRepository;
import com.shadow.books.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	LineItemRepository lineItemRepository;

	@Override
	public Orders add(Orders order) {

		order.setDeleted(false);
		order.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		order.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		order = orderRepository.save(order);

		lineItemRepository.setOrderIdAndStatus(order.getId(), order.getUserId());
		return order;
	}

	@Override
	public Orders update(Orders order) {

		order.setDeleted(false);
		order.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return orderRepository.save(order);
	}

	@Override
	public Page<Orders> getOrderList(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
}
