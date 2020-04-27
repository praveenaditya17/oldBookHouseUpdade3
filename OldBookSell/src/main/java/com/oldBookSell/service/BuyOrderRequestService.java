package com.oldBookSell.service;

import java.util.List;

import com.oldBookSell.dto.BuyOrderRequestDTO;
import com.oldBookSell.model.BuyOrderRequest;

public interface BuyOrderRequestService {
	
	public int saveRequest(BuyOrderRequestDTO bookDetail);

	public int getNotification();

	public List<BuyOrderRequest> getOrderRequest();

	public void deleteBookRequest(int requestBookId);

	void addDeliverAddress(int addressId, int deliveryPersonId);

	public Iterable<Object> deliverySellRequest(int deliveryId);

	public void updateBuyBookStatus(int buyOrderRequestId, String check_status);
	
//	public List<BuyOrderRequest> findRequest();
}
