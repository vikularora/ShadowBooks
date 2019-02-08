package com.shadow.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shadow.books.domain.LineItem;
import com.shadow.books.Constants.DBConstants;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

	List<LineItem> findByUserIdAndStatusAndOrderIdIsNull(Long userId, String string);

	List<LineItem> findByOrderId(Long orderId);

	List<LineItem> findByUserIdAndStatus(Long userId, String status);

//	void setDeleted(boolean bool);

	@Transactional
	@Modifying
	@Query(DBConstants.UPDATE_STATUS_AND_ORDERID)
	void setOrderIdAndStatus(@Param("orderId") Long orderId, @Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query(DBConstants.SET_DELETED)
	void setDeleted(@Param("productId") Long productId);

	LineItem findByUserIdAndStatusAndProductIdAndOrderIdIsNull(long userId, String string, Long productId);

}
