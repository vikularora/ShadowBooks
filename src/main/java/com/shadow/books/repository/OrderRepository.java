package com.shadow.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shadow.books.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

//	Page<Order> findByUserIdAndStatusNotInIgnoreCase(long userId, String status, Pageable page);

//	Page<Order> findByUserIdAndStatusNotInIgnoreCase(long userId, String status, Pageable page);

	
	Page<Order> findByStatusOrderByIdDesc(String status, Pageable page);

//	Page<Order> findByUserId(Long userId, Pageable page);

	Page<Order> findByUserIdOrderByIdDesc(Long userId, Pageable page);

}
