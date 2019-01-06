package com.shadow.books.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shadow.books.domain.Address;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

	Page<Address> findByUserId(Long user, Pageable pageRequest);

	@Transactional
	@Modifying
	@Query("update Address ad set ad.isSelected= 0 where ad.userId = :userId")
	void setSelected(@Param("userId") Long userId);

	List<Address> findByIsSelectedAndUserId(Boolean string, Long userId);

}
