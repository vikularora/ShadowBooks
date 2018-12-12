package com.shadow.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shadow.books.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserIdAndStatusNotInIgnoreCase(long userId, String status);

}
