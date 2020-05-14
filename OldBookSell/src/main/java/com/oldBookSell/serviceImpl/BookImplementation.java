package com.oldBookSell.serviceImpl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oldBookSell.dto.BookDTO;
import com.oldBookSell.model.Book;
import com.oldBookSell.repository.BookRepository;
import com.oldBookSell.service.BookService;

@Service
public class BookImplementation implements BookService {
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public void saveBook(BookDTO bookDTOObj) {
		Book bookObj=new Book();
		
		bookObj.setAmount(bookDTOObj.getAmount());
		bookObj.setAuthors(bookDTOObj.getAuthors());
		bookObj.setBookName(bookDTOObj.getBookName());
		bookObj.setBookStatus(bookDTOObj.getBookStatus());
		bookObj.setCategories(bookDTOObj.getCategories());
		bookObj.setCurrencyCode(bookDTOObj.getCurrencyCode());
		bookObj.setDescription(bookDTOObj.getDescription());
		bookObj.setIsbnNo1(bookDTOObj.getIsbnNo1());
		bookObj.setIsbnNo2(bookDTOObj.getIsbnNo2());
		bookObj.setIsbnType10(bookDTOObj.getIsbnType10());
		bookObj.setIsbnType13(bookDTOObj.getIsbnType13());
		bookObj.setPublishedDate(bookDTOObj.getPublishedDate());
		bookObj.setPublisher(bookDTOObj.getPublisher());
		bookObj.setQuantity(bookDTOObj.getQuantity());
		bookObj.setSmallThumbnail(bookDTOObj.getSmallThumbnail());
		bookObj.setThumbnail(bookDTOObj.getThumbnail());
		
		//this code for usefull for book tabel find the the unique book and update quatity
		Book abc=bookRepository.findByBookNameAndAuthor(bookDTOObj.getBookName(),bookDTOObj.getAuthors());
		
		try {	
			if(abc == null) {
				bookRepository.save(bookObj);
			}else {
				bookObj.setQuantity(abc.getQuantity()+1);
				bookObj.setAmount(abc.getAmount());
				bookObj.setBookId(abc.getBookId());
				bookRepository.save(bookObj);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	@Override
	public List<Book> findBooks(int min, int max) {
		return bookRepository.findBooks(min, max);
	}

	@Override
	public Book updateBookPrice(int bookId, int price) {
		Optional<Book> obj=bookRepository.findById(bookId);
		System.out.println(obj.get().getAmount());
		obj.get().setAmount(price);
		bookRepository.save(obj.get());
		return null;
	}
	
	@Override
	public List<Book> findBookByNameAuthorAndIsbn(String bookName) {
		return bookRepository.findBookByNameAuthorAndIsbn(bookName);
	}
	
	@Override
	public List<Book> getAllBook() {
		return bookRepository.getAllBook();
	}
	
	@Override
	public List<Book> getAllBook2() {
		return bookRepository.getAllBookForUpdate();
	}
	
	@Override
	public Optional<Book> findBookById(int bookId) {
		return bookRepository.findById(bookId);
	}

	@Override
	public int getQuantity(int bookId) {
		return bookRepository.getQuantity(bookId);
	}

	@Override
	public void minusQuantity(int bookId, int quantity) {
		Optional<Book> bookRequest =bookRepository.findByBookId(bookId);
		Book bookObj=new Book();
		bookObj=bookRequest.get();
		bookObj.setQuantity(bookObj.getQuantity()-quantity);
		System.out.println(bookObj.getQuantity());
		bookRepository.save(bookObj);
	}

	@Override
	public List<Book> findBookByCategory(String category) {
		return bookRepository.findBookByCategory(category);
	}
	
	@Override
	public List<Book> findBookByAuthor(String author){
		return bookRepository.findBookByAuthor(author);
	}
	@Override
	public List<Book> findBookByPublisher(String publisher){
		return bookRepository.findBookByPublisher(publisher);
	}

	@Override
	public List<Book> findAllBookForSell() {
		return bookRepository.findAllBookForSell();
	}
}
