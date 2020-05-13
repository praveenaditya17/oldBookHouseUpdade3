package com.oldBookSell.service;

import java.util.List;
import java.util.Optional;

import com.oldBookSell.dto.BookDTO;
import com.oldBookSell.model.Book;

public interface BookService {

	void saveBook(BookDTO bookDTOObj);

	List<Book> findBooks(int min, int max);

	Book updateBookPrice(int i, int j);

	List<Book> getAllBook();

	List<Book> getAllBook2();

	Optional<Book> findBookById(int bookId);

	List<Book> findBookByNameAuthorAndIsbn(String bookName);

	int getQuantity(int bookId);

	void minusQuantity(int bookId, int quantity);

	List<Book> findBookByCategory(String category);

	List<Book> findBookByAuthor(String author);

	List<Book> findBookByPublisher(String publisher);

}
