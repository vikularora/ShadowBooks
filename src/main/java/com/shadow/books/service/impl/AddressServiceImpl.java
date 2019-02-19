package com.shadow.books.service.impl;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	Logger logger = LogManager.getLogger(this.getClass());

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

		logger.info("BEFORE ADDRESS ADDED :: " + address);
		return addressRepository.save(address);

	}

	@Override
	public Address updateSelectedStatus(Address address) {
		changeSelected(address.getUserId());
		Optional<Address> optList = addressRepository.findById(address.getId());

		logger.info("OPTIONAL LIST OF ADDRESS" + optList);
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
			logger.info("BEFORE STATUS UPDATE OF ADDRESS" + address);
			return addressRepository.save(address);
		}
		return address;
	}

	public void changeSelected(long userId) {
		logger.info("IN CHANGE SELECTED :: " + userId);
		addressRepository.setSelected(userId);
	}

	@Override
	public Address update(Address address) {

		Optional<Address> optList = addressRepository.findById(address.getId());
		logger.info("OPTIONAL LIST OF ADDRESS :: " + optList);

		if (!optList.isPresent()) {
			logger.info("ADDRESS NOT FOUND :: " + address);
			return address;
		}
		address.setDeleted(false);
		address.setSelected(optList.get().isSelected());
		address.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		logger.info("BEFORE ADDRESS UPDATE :: " + address);
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
