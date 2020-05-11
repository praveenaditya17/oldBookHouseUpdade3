package com.oldBookSell.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oldBookSell.model.Book;

@Transactional
public interface BookRepository extends CrudRepository<Book, Integer>{
	
	@Query(value="select * from book where amount!=0 and book_name=?1 or authors=?2",nativeQuery = true)
	Book findByBookNameAndAuthor(String bookName, String authors);
	
	@Query(value = "select * from book where book_status='sell' limit ?1,?2",nativeQuery=true)
	List<Book> findBooks(int min, int max);

}
