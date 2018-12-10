package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Address;
import com.shadow.books.repository.AddressRespository;
import com.shadow.books.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRespository addressRepository;

	@Override
	public Address add(Address address) {
		address.setDeleted(false);
		address.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return addressRepository.save(address);
	}

	@Override
	public Address update(Address address) {
		address.setDeleted(false);
		address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return addressRepository.save(address);
	}

	@Override
	public Page<Address> list(int page, int size, Long userId) {
		Pageable pageable = PageRequest.of(page, size);
		return addressRepository.findByUserId(userId, pageable);

	}

	@Override
	public void delete(Long id) {
		addressRepository.deleteById(id);
	}

}
