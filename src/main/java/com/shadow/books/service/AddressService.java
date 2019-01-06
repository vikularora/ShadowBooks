package com.shadow.books.service;

import org.springframework.data.domain.Page;

import com.shadow.books.domain.Address;

public interface AddressService {


	Address add(Address address);
	
	Address updateSelectedStatus(Address address);

	Address update(Address address);

	Page<Address> list(int page, int size, Long userId);


	void delete(Long id);

}
