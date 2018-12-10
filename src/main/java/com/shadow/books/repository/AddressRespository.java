package com.shadow.books.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shadow.books.domain.Address;

@Repository
public interface AddressRespository extends PagingAndSortingRepository<Address, Long> {

	Page<Address> findByUserId(Long user, Pageable pageRequest);
	

}
