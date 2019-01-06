package com.shadow.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shadow.books.domain.OrderOnDemand;

@Repository
public interface OrderOnDemandRepository extends JpaRepository<OrderOnDemand, Long> {

}
