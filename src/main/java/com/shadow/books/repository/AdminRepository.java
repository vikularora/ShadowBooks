package com.shadow.books.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shadow.books.domain.Admin;

public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {

	Admin findByMobileNumber(String mobileNumber);

}
