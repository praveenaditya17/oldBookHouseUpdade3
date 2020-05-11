package com.oldBookSell.service;

import java.util.List;

import com.oldBookSell.dto.BookDTO;
import com.oldBookSell.model.Book;

public interface BookService {

	void saveBook(BookDTO bookDTOObj);

	List<Book> findBooks(int min, int max);

}
