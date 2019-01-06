package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shadow.books.domain.Address;
import com.shadow.books.repository.AddressRepository;
import com.shadow.books.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;

//	@Override
//	public Address add(Address address) {
//		address.setDeleted(false);
//		address.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
//		address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
//		return addressRepository.save(address);
//	}

	@Override
	public Address add(Address address) {
		changeSelected(address.getUserId());
		address.setDeleted(false);
		address.setSelected(true);
		address.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		return addressRepository.save(address);

	}

	@Override
	public Address updateSelectedStatus(Address address) {
		changeSelected(address.getUserId());
		Optional<Address> optList = addressRepository.findById(address.getId());
		if (optList.isPresent()) {
			address.setName(optList.get().getName());
			address.setHouseNumber(optList.get().getHouseNumber());
			address.setStreet(optList.get().getStreet());
			address.setArea(optList.get().getArea());
			address.setLandmark(optList.get().getLandmark());
			address.setContactNo(optList.get().getContactNo());
			address.setDeleted(false);
			address.setSelected(true);
			address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
			return addressRepository.save(address);
		}
		return address;
	}

	public void changeSelected(long userId) {
		addressRepository.setSelected(userId);
	}

	@Override
	public Address update(Address address) {

		Optional<Address> optList = addressRepository.findById(address.getId());

		address.setDeleted(false);
		address.setSelected(optList.get().isSelected());
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
