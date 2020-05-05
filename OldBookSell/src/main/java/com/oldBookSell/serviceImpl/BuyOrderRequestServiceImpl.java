package com.oldBookSell.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
		
		buyOrderRequestRepository.save(buyOrderRequestObj);
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
	public void addDeliverAddress(int addressId,int deliveryPersonId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		buyOrderRequestRepository.addDeliverAddress("pending",addressId,deliveryPersonId,authentication.getName(),"user");
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
}
