package com.shadow.books.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shadow.books.domain.User;

//public interface UsersRepository extends JpaRepository<User, Long>{
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	Optional<User> findByContactNo(String contactNo);

	Optional<User> findByContactNoOrDeletedIsNotNull(String contactNo);

}
