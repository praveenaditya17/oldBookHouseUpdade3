package com.oldBookSell.repository;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oldBookSell.model.SellOrderRequest;

@Transactional
public interface SellOrderRequestRepository extends CrudRepository<SellOrderRequest, Integer>{

	@Query(value="select * from sell_order_request where book_name=?1 and authors=?2",nativeQuery = true)
	SellOrderRequest findByBookNameAndAuthor(String BookName,String authors);
	
	@Query(value="select sell_order_request_id,book_name,authors,check_status,isbn_no1,isbn_no2,publisher, small_thumbnail,address, address2,district, postal_code,state,first_name,last_name,mobile_number from sell_order_request s, address a,user_details u where s.address_id = a.id and a.user_id = u.user_id and s.dilevery_person_id=?1",nativeQuery = true)
	Iterable<Object> deliveryPersonRequest(int i);
	
	@Modifying
	@Query(value="update sell_order_request set check_status=?1,feedback_by_delivery_person=?2 where sell_order_request_id=?3",nativeQuery = true)
	void updateBookStatus(String check_status, String feedBack, int sellOrderRequestId);
	
	@Query(value = "select * from sell_order_request limit ?1,?2",nativeQuery=true)
	List<SellOrderRequest> findBooks(int min, int max);
	
	@Query(value="select * from sell_order_request where book_name like %?1% or authors like %?1% or isbn_no1 like %?1% or isbn_no2 like %?1%",nativeQuery = true)
	List<SellOrderRequest> findBookByNameAuthorAndIsbn(String searchType);

	@Query(value="select * from sell_order_request where categories like %?1% or book_name like %?1%",nativeQuery=true)
	List<SellOrderRequest> findBookByCategory(String category);

	@Query(value="select sell_order_request_id,book_name,authors,check_status,isbn_no1,isbn_no2,publisher, small_thumbnail,address, address2,district, postal_code,state,first_name,last_name,mobile_number from sell_order_request s, address a,user_details u where s.address_id = a.id and a.user_id = u.user_id",nativeQuery = true)
	Iterable<Object> deliveryPersonRequestAdmin();
	
}
