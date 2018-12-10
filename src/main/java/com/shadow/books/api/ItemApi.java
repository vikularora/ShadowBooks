package com.shadow.books.api;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.shadow.books.domain.Item;
import com.shadow.books.service.ItemService;

@RestController
@RequestMapping("/items/")
public class ItemApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	ItemService itemService;

	@CrossOrigin
	@PostMapping("add")
	public ResponseEntity<Item> add(@RequestBody Item item) throws Exception {
		Item result = itemService.add(item);

		if (result != null) {
			return new ResponseEntity<Item>(result, HttpStatus.CREATED);
		}
		return new ResponseEntity<Item>(result, HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@PutMapping("update")
	public ResponseEntity<Item> update(@RequestBody Item item) throws Exception {

		item = itemService.update(item);

		if (item != null) {
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		}
		return new ResponseEntity<Item>(item, HttpStatus.NOT_MODIFIED);
	}

	@GetMapping("list")
	private ResponseEntity<Page<Item>> list(@RequestParam(name = "category", required = true) String category,
			@RequestParam(name = "language", required = true) String language,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Item> optList = itemService.listByCategoryGroupByLanguage(category, language, pageable);

		if (optList.isEmpty()) {
			return new ResponseEntity<Page<Item>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<Item>>(optList, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("list/{id}")
	public ResponseEntity<Optional<Item>> getById(@PathVariable(name = "id", required = true) Long id)
			throws Exception {
		Optional<Item> optItem = itemService.finById(id);

		if (optItem.isPresent()) {
			return new ResponseEntity<Optional<Item>>(optItem, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<Item>>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(name = "id", required = true) Long id) throws Exception {
		itemService.delete(id);
	}
}
