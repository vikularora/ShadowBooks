package com.shadow.books.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shadow.books.Constants.DBConstants;
import com.shadow.books.domain.Item;
import com.shadow.books.repository.ItemRepository;
import com.shadow.books.repository.LineItemRepository;
import com.shadow.books.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	final private String UPLOAD_DIRECTORY = DBConstants.UPLOAD_DIRECTORY_PATH;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	LineItemRepository lineItemRepository;

	Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public Item add(Item inventory) throws IOException {

		String imageUrl = saveImage(inventory);
		logger.info("AFTER IMAGE SAVE AND IMAGE URL IS :: " + imageUrl);
		inventory.setImageUrl(DBConstants.IMAGE_PATH + (imageUrl));
		inventory.setDeleted(false);
		inventory.setStatus(DBConstants.AVAILABLE);
		inventory.setCreatedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		inventory.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		logger.info("BEFORE ITEM IS ADDED INTO DATABASE :: " + inventory);
		return itemRepository.save(inventory);
	}

	private String saveImage(Item inventory) throws IOException {

		logger.info("SAVING IMG " + inventory);

		MultipartFile file = inventory.getFile();
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			File dir = new File(UPLOAD_DIRECTORY);
			if (!dir.exists())
				dir.mkdirs();
			String imageName = inventory.getCategory() + "-" + inventory.getLanguage() + "-" + inventory.getName()
					+ Calendar.getInstance().getTimeInMillis();
			File serverFile = new File(dir.getAbsolutePath() + File.separator + imageName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			logger.info("SERVER FILE LOCATION IS :: " + serverFile.getAbsolutePath());
			logger.info("SUCCESSFULLY UPLOADED FILE :: " + imageName);
			return imageName;
		} else {
			logger.info("FAILED TO UPLOAD IMAGE BECAUSE THE FILE WAS EMPTY.");
			return null;
		}

	}

	@Override
	public Item update(Item item) {
		item.setDeleted(false);
		item.setStatus(DBConstants.AVAILABLE);
		item.setModifiedOn(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
		logger.info("BEFORE UPDATE ITEM :: " + item);
		return itemRepository.save(item);
	}

	@Override
	public Page<Item> list(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Item> inventoryList = itemRepository.findAll(pageable);
		return inventoryList;
	}

	@Override
	public Optional<Item> finById(Long id) {
		Optional<Item> optItem = itemRepository.findById(id);
		if (optItem.isPresent()) {
			optItem.get().setDiscountedPrice(optItem.get().getPrice() * optItem.get().getDiscount() / 100);
		}
		return optItem;

	}

	@Override
	public void delete(Long id) {
		lineItemRepository.setDeleted(id);
		itemRepository.deleteById(id);

	}

	@Override
	public Page<Item> listByCategoryGroupByLanguage(String category, String language, Pageable pageable) {

		Page<Item> items = itemRepository.findByCategoryAndLanguageAllIgnoreCaseOrderByIdDesc(category, language,
				pageable);

		logger.info("PAGE LIST OF ITEMS :: " + items);

		items.getContent().forEach(item -> {
			float discount = item.getPrice() * item.getDiscount() / 100;
			item.setDiscountedPrice(item.getPrice() - discount);
		});
		return items;
	}

	@Override
	public Map<String, List<Item>> listByCategoryGroupByLanguage(String category, Pageable pageable) {
		Map<String, List<Item>> itemsMap = new HashMap<String, List<Item>>();
		List<String> languages = Arrays.asList(DBConstants.HINDI, DBConstants.ENGLISH, DBConstants.PUNJABI);

		languages.forEach(language -> {
			logger.info("FETCHING " + category + " FOR LANGUAGE : " + language);
//			Page<Item> languageItems = itemRepository.findByCategoryAndLanguageAllIgnoreCase(category, language, pageable);
			Page<Item> languageItems = itemRepository.findByCategoryAndLanguageAllIgnoreCaseOrderByIdDesc(category,
					language, pageable);
			if (!languageItems.isEmpty()) {
				logger.info("FETCHED  " + languageItems.getNumberOfElements() + " " + category + " FOR LANGUAGE : "
						+ language);
//				itemsMap.putAll(languageItems.get().collect(Collectors.groupingBy(Item::getLanguage)));
//				languageItems.get().collect(Collectors.groupingBy(Item::getLanguage)).entrySet().forEach(entry->itemsMap.put(entry.getKey(), entry.getValue()));

				languageItems.get().collect(Collectors.groupingBy(Item::getLanguage)).entrySet().forEach(entry -> {

					entry.getValue().forEach(item -> {
						float discount = item.getPrice() * item.getDiscount() / 100;
						item.setDiscountedPrice(item.getPrice() - discount);
					});
					itemsMap.put(entry.getKey(), entry.getValue());
				});

			}
		});
		return itemsMap;
	}

//	@Override
//	public Map<String, List<Item>> search(String name) {
//
////		List<Item> searchedItems = itemRepository.findByName(name);
//		List<Item> searchedItems = itemRepository.findByNameOrderByIdAsc(name);
//		logger.info("searchedItems.size() is :: " + searchedItems.size());
//		Map<String, List<Item>> itemsMap = new HashMap<>();
//
//		searchedItems.stream().collect(Collectors.groupingBy(Item::getCategory)).entrySet()
//				.forEach(entry -> itemsMap.put(entry.getKey(), entry.getValue()));
//		return itemsMap;
//
//	}

	@Override
	public List<Item> search(String name) {

		List<Item> items = itemRepository.findByNameStartingWithIgnoreCaseOrderByIdDesc(name);
		items.forEach(item -> {
			float discount = item.getPrice() * item.getDiscount() / 100;
			item.setDiscountedPrice(item.getPrice() - discount);
		});
		return items;
	}
}
