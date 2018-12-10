package com.shadow.books.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shadow.books.domain.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

	Page<LineItem> findByUserIdAndStatusAndOrderIdIsNull(Long userId, String string, Pageable pageable);

//	@Modifying
//	@Query("update line_item li set li.status ="ordered", li.order_id=:orderId where li.status="added" and li.userId=:userId")
	@Transactional
	@Modifying
	@Query("update LineItem li set li.status= 'ordered', li.orderId=:orderId where li.status='added' and li.userId = :userId")
	void setOrderIdAndStatus(@Param("orderId") Long orderId, @Param("userId") Long userId);
//	User findByLastnameOrFirstname(@Param("lastname") String lastname,  @Param("firstname") String firstname);
//	@Modifying
//	@Query("update User u set u.firstname = ?1 where u.lastname = ?2")

	
}
