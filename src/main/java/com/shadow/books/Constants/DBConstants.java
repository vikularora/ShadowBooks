package com.shadow.books.Constants;

public class DBConstants {

	// FILE DIRECTORY
	public static final String UPLOAD_DIRECTORY_PATH = "/home/ubuntu/shadow-images";
//	public static final String UPLOAD_DIRECTORY_PATH = "/home/vikul/Desktop/shadow-images";
	public static final String IMAGE_PATH = "/items/picture?imgPath=";

	// LANGUAGES
	public static final String HINDI = "HINDI";
	public static final String PUNJABI = "PUNJABI";
	public static final String ENGLISH = "ENGLISH";

	
	// STATUS 
	public static final String PENDING = "Pending";
	public static final String IN_CART = "InCart";
	public static final String CANCELLED = "Cancelled";
	public static final String BOOK_OUT_OF_STOCK = "Book Out of Stock";

	// ITEMS STATUS
	public static final String AVAILABLE = "Available";
	public static final String UNAVAILABLE = "Unavailable";

	// QUERIES
	public static final String UPDATE_STATUS_AND_ORDERID ="update LineItem li set li.status= 'Pending', li.orderId=:orderId where li.status='InCart' and li.userId = :userId";
	public static final String SET_DELETED = "update LineItem li set li.deleted= 1 where li.productId = :productId";
}
