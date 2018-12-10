package com.shadow.books.util;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.shadow.books.domain.LineItem;

public class LineItemCart {


	private Long userId;
	@Size(min = 1, message = "hey! Let we go")
	private Set<LineItem> lineItems;

}
