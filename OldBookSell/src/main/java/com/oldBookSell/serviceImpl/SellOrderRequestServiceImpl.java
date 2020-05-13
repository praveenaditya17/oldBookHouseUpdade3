package com.oldBookSell.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oldBookSell.dto.SellOrderRequestDTO;
import com.oldBookSell.model.SellOrderRequest;
import com.oldBookSell.repository.SellOrderRequestRepository;
import com.oldBookSell.service.SellOrderRequestService;

@Service
public class SellOrderRequestServiceImpl implements SellOrderRequestService {
	
	@Autowired
	SellOrderRequestRepository sellOrderRequest;
	
	@Override
	@Transactional
	public SellOrderRequestDTO bookRequest(SellOrderRequestDTO sellOrderRequestDTO) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		SellOrderRequest sellOrderRequestObj=new SellOrderRequest();
		
		sellOrderRequestObj.setBookName(sellOrderRequestDTO.getBook_name());
		sellOrderRequestObj.setAuthors(sellOrderRequestDTO.getAuthors());
		sellOrderRequestObj.setDescription(sellOrderRequestDTO.getDescription());
		sellOrderRequestObj.setPublisher(sellOrderRequestDTO.getPublisher());
		sellOrderRequestObj.setPublishedDate(sellOrderRequestDTO.getPublishedDate());
		sellOrderRequestObj.setCategories(sellOrderRequestDTO.getCategories());
		sellOrderRequestObj.setIsbnType10(sellOrderRequestDTO.getIsbn_type_10());
		sellOrderRequestObj.setIsbnNo1(sellOrderRequestDTO.getIsbnNo1());
		sellOrderRequestObj.setIsbnType13(sellOrderRequestDTO.getIsbn_type_13());
		sellOrderRequestObj.setIsbnNo2(sellOrderRequestDTO.getIsbnNo2());
		sellOrderRequestObj.setSmallThumbnail(sellOrderRequestDTO.getSmallThumbnail());
		sellOrderRequestObj.setThumbnail(sellOrderRequestDTO.getThumbnail());
		sellOrderRequestObj.setAmount(sellOrderRequestDTO.getAmount());
		sellOrderRequestObj.setCurrencyCode(sellOrderRequestDTO.getCurrencyCode());
		sellOrderRequestObj.setQuantity(sellOrderRequestDTO.getQuantity());
		sellOrderRequestObj.setCheckStatus(sellOrderRequestDTO.getCheck_status());
		sellOrderRequestObj.setUserId(authentication.getName());
		sellOrderRequestObj.setAddressId(sellOrderRequestDTO.getAddressId());
		sellOrderRequestObj.setDileveryPersonId(sellOrderRequestDTO.getDileveryPersonId());
		//this code for usefull for book tabel find the the unique book and update quatity
//		SellOrderRequest abc=sellOrderRequest.findByBookNameAndAuthor(sellOrderRequestDTO.getBook_name(),sellOrderRequestDTO.getAuthors());
//		
//		try {	
//			
//			if(abc == null) {
//				sellOrderRequest.save(sellOrderRequestObj);
//			}else {
//				sellOrderRequestObj.setQuantity(abc.getQuantity()+1);
//				sellOrderRequestObj.setSellOrderRequestId(abc.getSellOrderRequestId());
//				sellOrderRequest.save(sellOrderRequestObj);
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		// delivery person logic
//		List list = sellOrderRequest.findAllByRole()
		sellOrderRequest.save(sellOrderRequestObj);
		return sellOrderRequestDTO;
		
	}

	@Override
	public Iterable<Object> deliveryRequest(int deliveryPerson_id) {
		Iterable<Object>result= sellOrderRequest.deliveryPersonRequest(deliveryPerson_id);
		return result;
	}

	@Override
	public void updateBookStatus(SellOrderRequestDTO sellOrderRequestDTO) {
		sellOrderRequest.updateBookStatus(sellOrderRequestDTO.getCheck_status(),sellOrderRequestDTO.getFeedBack(),sellOrderRequestDTO.getSellOrderRequestId());	
	}

	@Override
	public List<SellOrderRequest> findBooks(int min, int max) {
		
		return sellOrderRequest.findBooks(min, max);
	}

	@Override
	public List<SellOrderRequest> findBookByNameAuthorAndIsbn(String bookName) {
		return sellOrderRequest.findBookByNameAuthorAndIsbn(bookName);
	}

	@Override
	public Optional<SellOrderRequest> findBookById(int bookId) {
		return sellOrderRequest.findById(bookId);
	}

	@Override
	public List<SellOrderRequest> findBookByCategory(String category) {
		return sellOrderRequest.findBookByCategory(category);
	}

	@Override
	public Iterable<Object> deliveryRequestAdmin() {
		Iterable<Object>result= sellOrderRequest.deliveryPersonRequestAdmin();
		return result;
	}

	@Override
	public List<SellOrderRequest> findBookByAuthor(String author){
		return sellOrderRequest.findBookByAuthor(author);
	}
	@Override
	public List<SellOrderRequest> findBookByPublisher(String publisher){
		return sellOrderRequest.findBookByPublisher(publisher);
	}

	@Override
	public List<SellOrderRequest> findSellHistory() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(sellOrderRequest.findSellHistory(authentication.getName()));
		return sellOrderRequest.findSellHistory(authentication.getName());
	}

	@Override
	public List<SellOrderRequest> getAllBook() {
		
		return sellOrderRequest.getAllBook();
	}
	
	@Override
	public List<SellOrderRequest> getAllBook2() {
		
		return sellOrderRequest.getAllBookForUpdate();
	}

	@Override
	public List<SellOrderRequest> updateBookPrice(int bookId, float price) {
		Optional<SellOrderRequest> obj=sellOrderRequest.findById(bookId);
//		System.out.println(obj.get().getAmount());
		obj.get().setAmount(price);
		sellOrderRequest.save(obj.get());
		return null;
	}
	
	
}
