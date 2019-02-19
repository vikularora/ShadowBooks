package com.shadow.books.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shadow.books.Constants.DBConstants;
import com.shadow.books.domain.Item;
import com.shadow.books.dto.ItemDto;
import com.shadow.books.service.ItemService;

@RestController
@RequestMapping("/items/")
public class ItemApi {

	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	ItemService itemService;

	final private String UPLOAD_DIRECTORY = DBConstants.UPLOAD_DIRECTORY_PATH;

	@PostMapping("add")
	public ResponseEntity<Item> add(@ModelAttribute Item item) throws Exception {

		logger.info("CALLING ADD ITEM :: " + item);
		Item addedItem = itemService.add(item);
		logger.info("ADDED ITEMS :: " + addedItem);

		if (addedItem != null) {
			addedItem.setFile(null);
			return new ResponseEntity<Item>(addedItem, HttpStatus.CREATED);
		}
		return new ResponseEntity<Item>(addedItem, HttpStatus.NO_CONTENT);
	}

//	@PostMapping("addImage")
//	public ResponseEntity<Item> addImage(@RequestParam("file") MultipartFile file) throws Exception {
//
//		logger.info("ADD IMAGE ::" + file.getBytes());
//
//		return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
//
//	}

	@PutMapping("update")
	public ResponseEntity<Item> update(@RequestBody Item item) throws Exception {

		logger.info("CALLING UPDATE ITEM :: " + item);
		item = itemService.update(item);
		logger.info("UPDATED ITEM :: " + item);

		if (item != null) {
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		}
		return new ResponseEntity<Item>(item, HttpStatus.NOT_MODIFIED);
	}

//	@GetMapping("category/{cat}")
//	private ResponseEntity<JSONArray> listByCategory(@PathVariable(name = "cat", required = true) String cat,
//			@RequestParam(required = false, name = "size", defaultValue = "7") int size,
//			@RequestParam(required = false, name = "page", defaultValue = "0") int page) {
//		
//		JSONArray jsonArray = new JSONArray();
//
//		Pageable pageable = PageRequest.of(page, size);
//		Map<String, List<Item>> itemsMap = itemService.listByCategoryGroupByLanguage(cat, pageable);
//
//		if (itemsMap.isEmpty()) {
//			return new ResponseEntity<JSONArray>(HttpStatus.NO_CONTENT);
//		}
//		itemsMap.entrySet().forEach(entry -> {
//			JSONObject jsonObject = new JSONObject();
//			try {
//				jsonObject.put("language", entry.getKey());
//				jsonObject.put("list", entry.getValue());
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			jsonArray.put(jsonObject);
//		});
//		return new ResponseEntity<JSONArray>(jsonArray, HttpStatus.OK);
//	}

	@GetMapping("category/{cat}")
	private ResponseEntity<List<ItemDto>> listByCategory(@PathVariable(name = "cat", required = true) String cat,
			@RequestParam(required = false, name = "size", defaultValue = "5") int size,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page) {

		List<ItemDto> list = new ArrayList<ItemDto>();
		Pageable pageable = PageRequest.of(page, size);

		logger.info("CALLING LIST OF ITEMS ON THE BASIS OF CATEGORY :: " + cat);
		Map<String, List<Item>> itemsMap = itemService.listByCategoryGroupByLanguage(cat, pageable);
		logger.info("LIST OF ITEMS IN MAP FORM :: " + itemsMap);

		if (itemsMap.isEmpty()) {
			return new ResponseEntity<List<ItemDto>>(HttpStatus.NO_CONTENT);
		}
		itemsMap.entrySet().forEach(entry -> {

			ItemDto itemDto = new ItemDto();
			itemDto.setLanguage(entry.getKey());
			itemDto.setList(entry.getValue());
			list.add(itemDto);
		});
		logger.info("LIST OF ITEMS :: " + list);
		return new ResponseEntity<List<ItemDto>>(list, HttpStatus.OK);
	}

	@GetMapping("list")
	private ResponseEntity<Page<Item>> listByCategoryAndLanguage(
			@RequestParam(name = "category", required = true) String category,
			@RequestParam(name = "language", required = true) String language,
			@RequestParam(required = false, name = "size", defaultValue = "10") int size,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page) {

		logger.info("CALLING LIST OF CATEGORY :: " + category + " AND LANGUAGE ::" + language);
		Pageable pageable = PageRequest.of(page, size);
		Page<Item> optList = itemService.listByCategoryGroupByLanguage(category, language, pageable);
		logger.info("LIST OF ITEMS  :: " + optList);

		if (optList.isEmpty()) {
			return new ResponseEntity<Page<Item>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<Item>>(optList, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("list/{id}")
	public ResponseEntity<Optional<Item>> getById(@PathVariable(name = "id", required = true) Long id)
			throws Exception {

		logger.info("CALLING LIST BY ID :: " + id);
		Optional<Item> optItem = itemService.finById(id);
		logger.info("LIST BY ID ITEM :: " + optItem);

		if (optItem.isPresent()) {
			return new ResponseEntity<Optional<Item>>(optItem, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<Item>>(HttpStatus.NO_CONTENT);
	}

	@CrossOrigin
	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable(name = "id", required = true) Long id) throws Exception {
		logger.info("CALLING DELETE ITEM :: " + id);
		itemService.delete(id);
	}

//	@GetMapping("search")
//	private ResponseEntity<Map<String, List<Item>>> search (@RequestParam("name") String name){
//		 Map<String, List<Item>> searchedItems = itemService.search(name);
//
//			if (searchedItems.isEmpty()) {
//				return new ResponseEntity<Map<String, List<Item>>>(HttpStatus.NO_CONTENT);
//			}
//			return new ResponseEntity<Map<String, List<Item>>>(searchedItems, HttpStatus.OK);
//	}
	@GetMapping("search")
	private ResponseEntity<List<Item>> search(@RequestParam("name") String name) {

		logger.info("SEARCH ITEM BY NAME :: " + name);
		List<Item> searchedItems = itemService.search(name);
		logger.info("SEARCHED ITEMS :: " + searchedItems);

		if (searchedItems.isEmpty()) {
			return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(searchedItems, HttpStatus.OK);
	}

	@GetMapping("picture")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("imgPath") String imgPath) {

		logger.info("CALLING GET IMAGE :: " + imgPath);

		String rpath = UPLOAD_DIRECTORY;
		rpath = rpath + "/" + imgPath; // whatever path you used for storing the file
		Path path = Paths.get(rpath);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imgPath + "\"")
				.body(new ByteArrayResource(data));
	}

}
