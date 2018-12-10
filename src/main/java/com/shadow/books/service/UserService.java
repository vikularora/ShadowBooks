package com.shadow.books.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.shadow.books.domain.User;

public interface UserService {

	User add(User user);

	User update(User user);

	Optional<User> finById(Long id);

	void delete(Long id);

	Page<User> list(int page, int size);

}
