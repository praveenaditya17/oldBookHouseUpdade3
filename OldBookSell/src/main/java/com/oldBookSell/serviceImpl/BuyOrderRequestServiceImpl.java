	package com.oldBookSell.serviceImpl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.oldBookSell.dto.BuyOrderRequestDTO;
import com.oldBookSell.model.BuyOrderRequest;
import com.oldBookSell.repository.BuyOrderRequestRepository;
import com.oldBookSell.service.BuyOrderRequestService;

@Service
public class BuyOrderRequestServiceImpl implements BuyOrderRequestService {

	@Autowired
	BuyOrderRequestRepository buyOrderRequestRepository;
	
	@Override
	public int saveRequest(BuyOrderRequestDTO buyOrderRequestDto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BuyOrderRequest buyOrderRequestObj=new BuyOrderRequest();
		
		buyOrderRequestObj.setBookName(buyOrderRequestDto.getBookName());
		buyOrderRequestObj.setAuthors(buyOrderRequestDto.getAuthors());
		buyOrderRequestObj.setSmallThumbnail(buyOrderRequestDto.getSmallThumbnail());
		buyOrderRequestObj.setAmount(buyOrderRequestDto.getAmount());
		buyOrderRequestObj.setQuantity(buyOrderRequestDto.getQuantity());
		buyOrderRequestObj.setCheckStatus(buyOrderRequestDto.getCheckStatus());
		buyOrderRequestObj.setBookId(buyOrderRequestDto.getBookId());
		buyOrderRequestObj.setUserId(authentication.getName());
		buyOrderRequestObj.setAddressId(buyOrderRequestDto.getAddressId());
		buyOrderRequestObj.setDileveryPersonId(buyOrderRequestDto.getDileveryPersonId());
		buyOrderRequestObj.setStatus(buyOrderRequestDto.getStatus());
		buyOrderRequestObj.setTransactionId(buyOrderRequestDto.getTransactionId());
		
		BuyOrderRequest result=buyOrderRequestRepository.checkBook(authentication.getName(),buyOrderRequestDto.getBookId(),"user");
		if(result!=null) {
			addQuantity(result.getBuyOrderRequestId());
			System.out.println(result.getBuyOrderRequestId());
		}else {
			buyOrderRequestRepository.save(buyOrderRequestObj);
		}
		
		
		return buyOrderRequestRepository.countOrderRequest(authentication.getName(),"user");
	}
	
	@Override
	public int getNotification() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return buyOrderRequestRepository.countOrderRequest(authentication.getName(),"user");
	}

	@Override
	public List<BuyOrderRequest> getOrderRequest() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return buyOrderRequestRepository.getOrderRequest(authentication.getName(),"user");
	}

	@Override
	public void deleteBookRequest(int requestBookId) {
		buyOrderRequestRepository.deleteById(requestBookId);
	}

	@Override
	public Iterable<Object> deliverySellRequest(int deliveryId) {
		Iterable<Object>result= buyOrderRequestRepository.deliveryPersonRequest(deliveryId);
		return result;
	}

	@Override
	public void updateBuyBookStatus(int buyOrderRequestId, String check_status) {
		buyOrderRequestRepository.updateBuyBookStatus(buyOrderRequestId,check_status);
	}

	@Override
	public Iterable<Object> deliverySellRequestAdmin() {
		return buyOrderRequestRepository.deliveryGetAdmin();
	}

	@Override
	public List<BuyOrderRequest> addQuantity(int requestBookId) {
		System.out.println(requestBookId);
		Optional<BuyOrderRequest> buyOrderRequest =buyOrderRequestRepository.findById(requestBookId);
		BuyOrderRequest buyOrderRequestObj=new BuyOrderRequest();
		buyOrderRequestObj=buyOrderRequest.get();
		buyOrderRequestObj.setQuantity(buyOrderRequestObj.getQuantity()+1);
		System.out.println(buyOrderRequestObj.getQuantity());
		buyOrderRequestRepository.save(buyOrderRequestObj);
		return null;
	}

	@Override
	public List<BuyOrderRequest> minusQuantity(int requestBookId) {
		Optional<BuyOrderRequest> buyOrderRequest =buyOrderRequestRepository.findById(requestBookId);
		BuyOrderRequest buyOrderRequestObj=new BuyOrderRequest();
		buyOrderRequestObj=buyOrderRequest.get();
		buyOrderRequestObj.setQuantity(buyOrderRequestObj.getQuantity()-1);
		System.out.println(buyOrderRequestObj.getQuantity());
		buyOrderRequestRepository.save(buyOrderRequestObj);
		return null;
	}

	@Override
	public List<BuyOrderRequest> findBuyHistory(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return buyOrderRequestRepository.findBuyHistory(authentication.getName());
	}

	@Override
	public List<BuyOrderRequest> addDeliverAddress(int addressId, int deliveryPersonId, String status, String transactionId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<BuyOrderRequest> result= buyOrderRequestRepository.getBuyRequest(authentication.getName(),"user");
		buyOrderRequestRepository.addDeliverAddress("ProcessingOrder",addressId,deliveryPersonId,status,transactionId,authentication.getName(),"user");
		return result;
	}

	@Override
	public int getQuantity(int bookId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		BuyOrderRequest result=buyOrderRequestRepository.checkBook(authentication.getName(),bookId,"user");
		if(result!=null)
			return result.getQuantity();
		else
			return 0;
	}
}
