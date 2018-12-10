package com.shadow.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shadow.books.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
