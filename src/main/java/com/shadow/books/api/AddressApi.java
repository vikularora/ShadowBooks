package com.shadow.books.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.books.domain.Address;
import com.shadow.books.service.AddressService;

@RestController
@RequestMapping("/users/")
public class AddressApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private AddressService addressService;

	@CrossOrigin
	@PostMapping("{userId}/address/add")
	public ResponseEntity<Address> add(@PathVariable(name = "userId", required = true) Long userId,
			@RequestBody Address address) throws Exception {

		if (userId < 1) {
			return null;
		}
		address.setUserId(userId);

		logger.info("CAllING ADDRESS ADD :: " + address);
		Address addedAddress = addressService.add(address);

		logger.info("ADDED ADDRESS :: " + addedAddress);
		if (addedAddress != null) {
			return new ResponseEntity<Address>(addedAddress, HttpStatus.CREATED);
		}
		return new ResponseEntity<Address>(addedAddress, HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@PutMapping("{userId}/address/update/status")
	public ResponseEntity<Address> updateSelectedStatus(@PathVariable(name = "userId", required = true) Long userId,
			@RequestBody Address address) throws Exception {

		address.setUserId(userId);
		logger.info("UPDATE ADDRESS STATUS :: " + address);

		Address updatedAddress = addressService.updateSelectedStatus(address);
		logger.info("UPDATED STATUS :: " + updatedAddress);

		if (updatedAddress != null) {
			return new ResponseEntity<Address>(updatedAddress, HttpStatus.OK);
		}
		return new ResponseEntity<Address>(updatedAddress, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@PutMapping("{userId}/address/update")
	public ResponseEntity<Address> update(@PathVariable(name = "userId", required = true) Long userId,
			@RequestBody Address address) throws Exception {

		if (userId < 1) {
			return null;
		}
		address.setUserId(userId);

		logger.info("CALLING UPDATE ADDRESS :: " + address);
		address = addressService.update(address);
		logger.info("UPDATED ADDRESS :: " + address);

		if (address != null) {
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		}
		return new ResponseEntity<Address>(address, HttpStatus.NOT_MODIFIED);
	}

	@CrossOrigin
	@GetMapping("{userId}/address/list")
	public ResponseEntity<Page<Address>> list(@PathVariable(name = "userId", required = true) Long userId,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "filter") String filter) throws Exception {

		logger.info("CALLING ADDRESS LIST AND USERID IS :: " + userId);
		Page<Address> addressList = addressService.list(page, size, userId);

		logger.info("ADDRESS LIST ::" + addressList);

		if (addressList.getContent() == null) {
			return new ResponseEntity<Page<Address>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<Address>>(addressList, HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping("{userId}/address/delete/{id}")
	public void delete(@PathVariable(name = "id", required = true) Long id) throws Exception {
		logger.info("CALLING DELETE ADDRESS");
		addressService.delete(id);
	}
}
