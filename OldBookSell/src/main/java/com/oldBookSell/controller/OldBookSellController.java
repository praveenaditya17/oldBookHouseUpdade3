package com.oldBookSell.controller;

import java.security.Principal;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oldBookSell.dto.BuyOrderRequestDTO;
import com.oldBookSell.dto.OldBookSellDTO;
import com.oldBookSell.dto.SellOrderRequestDTO;
import com.oldBookSell.model.BuyOrderRequest;
import com.oldBookSell.model.SellOrderRequest;
import com.oldBookSell.model.UserDetails;
import com.oldBookSell.service.BuyOrderRequestService;
import com.oldBookSell.service.OldBookSellServices;
import com.oldBookSell.service.SellOrderRequestService;

@RestController
@RequestMapping
@CrossOrigin
public class OldBookSellController {
	
		@Autowired
		OldBookSellServices oldBookSellServices;
		
		@Autowired
		SellOrderRequestService sellOrderRequestService;
		
		@Autowired
		BuyOrderRequestService buyOrderRequestService;
		
		@GetMapping("/hello")
		public  String hello(Principal principal) {
			return "hello fundtion excute sucessfully";
		}
		
		@RequestMapping("/add")
		public OldBookSellDTO createUser(@RequestBody OldBookSellDTO userDetail) {
			return  oldBookSellServices.createUser(userDetail);
		}
		
		@GetMapping("/listUser")
		public Iterable<UserDetails> userList(){
			return oldBookSellServices.userList();
		}
		

		@RequestMapping("/fetchUser")
		public UserDetails getUserById(@RequestBody int id) {
			return oldBookSellServices.findById(id).get();
		}
		
		
		@RequestMapping("/updateUser")
		public UserDetails updateUser(@RequestBody UserDetails user){
			return oldBookSellServices.updateUser(user).get();
		}
		
		@RequestMapping("/deleteUser")
		public int deleteUser(@RequestBody int userId ){
			return oldBookSellServices.deleteUser(userId);
		}
		
		
		@RequestMapping("/bookDetailsRequest")
		public SellOrderRequestDTO addBookDetails(@RequestBody SellOrderRequestDTO sellOrderRequestDTO) {
			int deliveryId=oldBookSellServices.getDeliveryPerson();
			sellOrderRequestDTO.setDileveryPersonId(deliveryId);
			return sellOrderRequestService.bookRequest(sellOrderRequestDTO);
		}
		
		@RequestMapping("/addAddress")
		public UserDetails addAddress(@RequestBody OldBookSellDTO userDetail) {
			return  oldBookSellServices.addAddress(userDetail);
		}
		
		@GetMapping("/getAddress")
		public UserDetails getAddress() {
			return oldBookSellServices.getAddress();
		}
		
		@GetMapping("/getRole")
		public String getRole() {
			return oldBookSellServices.getRole();
		}
		
		@GetMapping("/getRequest")
		public Iterable<Object> getDeliveryRequest() {
			int deliveryId=oldBookSellServices.getDeliveryPersonId();
			System.out.println(deliveryId+".............");
			return sellOrderRequestService.deliveryRequest(deliveryId);
		}
		
		@RequestMapping("/bookStatus")
		public void updateBookStatus(@RequestBody SellOrderRequestDTO sellOrderRequestDTO) {
			System.out.println(sellOrderRequestDTO.getSellOrderRequestId()+","+sellOrderRequestDTO.getCheck_status()+","+sellOrderRequestDTO.getFeedBack());
			sellOrderRequestService.updateBookStatus(sellOrderRequestDTO);
		}
		
		
		@GetMapping("/findBooks/{min}/{max}")
		public List<SellOrderRequest> getBook(@PathVariable(value = "min")int min,@PathVariable(value = "max")int max){
			return sellOrderRequestService.findBooks(min,max);
		}
		
		@RequestMapping("/searchBook")
		public List<SellOrderRequest> searchBook(@RequestBody String bookName) {
//			System.out.println(bookName);
			return sellOrderRequestService.findBookByNameAuthorAndIsbn(bookName);	
		}
		
		@RequestMapping("/fetch")
		public Optional<SellOrderRequest> getBook(@RequestBody int bookId) {
//			System.out.println(bookName);
			return sellOrderRequestService.findBookById(bookId);	
		}
		
		@RequestMapping("/sellBookRequest")
		public int addBuyOrderRequest(@RequestBody int bookId) {
			
			Optional<SellOrderRequest> sellOrderRequest=sellOrderRequestService.findBookById(bookId);
			BuyOrderRequestDTO buyOrderRequestDTO =new BuyOrderRequestDTO();
			
			buyOrderRequestDTO.setBookName(sellOrderRequest.get().getBookName());
			buyOrderRequestDTO.setAuthors(sellOrderRequest.get().getAuthors());
			buyOrderRequestDTO.setSmallThumbnail(sellOrderRequest.get().getSmallThumbnail());
			buyOrderRequestDTO.setAmount(sellOrderRequest.get().getAmount());
			buyOrderRequestDTO.setQuantity(sellOrderRequest.get().getQuantity());
			buyOrderRequestDTO.setCheckStatus("user");
//			buyOrderRequestDTO.setShowDetail("user");
			buyOrderRequestDTO.setBookId(sellOrderRequest.get().getSellOrderRequestId());

//			int deliveryId=oldBookSellServices.getDeliveryPerson();
//			buyOrderRequestDTO.setDileveryPersonId(deliveryId);
		
			return buyOrderRequestService.saveRequest(buyOrderRequestDTO);
		}
		
		@RequestMapping("/getNotification")
		public int getNotification() {
			return buyOrderRequestService.getNotification();
		}
		
		@RequestMapping("/getBuyBook")
		public List<BuyOrderRequest> getOderRequest(){
			return buyOrderRequestService.getOrderRequest();
		}
		
		@RequestMapping("/deleteBookRequest")
		public int deleteBookRequest(@RequestBody int requestBookId){
			buyOrderRequestService.deleteBookRequest(requestBookId);
			return 0;
		}
		
		@RequestMapping("/addDeliverAddress")
		public int addDeliveryAddress(@RequestBody int addressId){
			int deliveryId=oldBookSellServices.getDeliveryPerson();
			buyOrderRequestService.addDeliverAddress(addressId,deliveryId);
			return 0;
		}
		
		@RequestMapping("/addDeliverAddressSingleBook")
		public int addDeliveryAddressSingleBook(@RequestBody int array[]){
			int addressID=array[0];
			int bookId=array[1];
			
			Optional<SellOrderRequest> sellOrderRequest=sellOrderRequestService.findBookById(bookId);
			BuyOrderRequestDTO buyOrderRequestDTO =new BuyOrderRequestDTO();
			
			buyOrderRequestDTO.setBookName(sellOrderRequest.get().getBookName());
			buyOrderRequestDTO.setAuthors(sellOrderRequest.get().getAuthors());
			buyOrderRequestDTO.setSmallThumbnail(sellOrderRequest.get().getSmallThumbnail());
			buyOrderRequestDTO.setAmount(sellOrderRequest.get().getAmount());
			buyOrderRequestDTO.setQuantity(sellOrderRequest.get().getQuantity());
			buyOrderRequestDTO.setCheckStatus("pending");
			buyOrderRequestDTO.setAddressId(addressID+"");
			buyOrderRequestDTO.setBookId(sellOrderRequest.get().getSellOrderRequestId());

			int deliveryId=oldBookSellServices.getDeliveryPerson();
			buyOrderRequestDTO.setDileveryPersonId(deliveryId);

			return buyOrderRequestService.saveRequest(buyOrderRequestDTO);
		}
		
		@GetMapping("/getSellRequest")
		public Iterable<Object> getDeliverySellRequest() {
			int deliveryId=oldBookSellServices.getDeliveryPersonId();
//			System.out.println(deliveryId+".............");
			return buyOrderRequestService.deliverySellRequest(deliveryId);
		}
		
		@RequestMapping("/updateBuyBookStatus")
		public void updateBuyBookStatus(@RequestBody SellOrderRequestDTO sellOrderRequestDTO) {
			System.out.println(sellOrderRequestDTO.getSellOrderRequestId()+","+sellOrderRequestDTO.getCheck_status()+","+sellOrderRequestDTO.getFeedBack());
			buyOrderRequestService.updateBuyBookStatus(sellOrderRequestDTO.getSellOrderRequestId(),sellOrderRequestDTO.getCheck_status());
		}
		
		@RequestMapping("/fetchCategory")
		public List<SellOrderRequest> findBookByCategory(@RequestBody String category){
//			Logger.info("Controller findBookByCategory method is caiing....");
//			LOGGER.info("In Controller findBookByCategory method Category=" +category);
			return sellOrderRequestService.findBookByCategory(category);
		}
		
}
