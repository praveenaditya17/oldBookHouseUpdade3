package com.oldBookSell.controller;

import java.security.Principal;




import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oldBookSell.dto.BookDTO;
import com.oldBookSell.dto.BuyOrderRequestDTO;
import com.oldBookSell.dto.OldBookSellDTO;
import com.oldBookSell.dto.PaymentDTO;
import com.oldBookSell.dto.SellOrderRequestDTO;
import com.oldBookSell.model.BuyOrderRequest;
import com.oldBookSell.model.Payment;
import com.oldBookSell.model.SellOrderRequest;
import com.oldBookSell.model.UserDetails;
import com.oldBookSell.service.BookService;
import com.oldBookSell.service.BuyOrderRequestService;
import com.oldBookSell.service.OldBookSellServices;
import com.oldBookSell.service.PaymentService;
import com.oldBookSell.service.SellOrderRequestService;
import com.stripe.model.Charge;

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
		
		@Autowired
		BookService bookService;
		
		@Autowired
		PaymentService paymentService;
		
		@GetMapping("/hello")
		public  String hello(Principal principal) {
			return "hello fundtion excute sucessfully";
		}
		
		@RequestMapping("/add")
		public OldBookSellDTO createUser(@RequestBody OldBookSellDTO userDetail) {
			return  oldBookSellServices.createUser(userDetail);
		}
		@RequestMapping("/forgetPassword")
		public void forgetPassword(@RequestBody String userName) {
			oldBookSellServices.changePassword(userName);
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
			
			if(sellOrderRequestDTO.getCheck_status().equals("Sucess")) {
				Optional<SellOrderRequest> sellOrderRequest=sellOrderRequestService.findBookById(sellOrderRequestDTO.getSellOrderRequestId());
				BookDTO bookDTOObj = new BookDTO();
				bookDTOObj.setAmount(sellOrderRequest.get().getAmount());
				bookDTOObj.setAuthors(sellOrderRequest.get().getAuthors());
				bookDTOObj.setBookName(sellOrderRequest.get().getBookName());
				bookDTOObj.setBookStatus("sell");
				bookDTOObj.setCategories(sellOrderRequest.get().getCategories());
				bookDTOObj.setCurrencyCode(sellOrderRequest.get().getCurrencyCode());
				bookDTOObj.setDescription(sellOrderRequest.get().getDescription());
				bookDTOObj.setIsbnNo1(sellOrderRequest.get().getIsbnNo1());
				bookDTOObj.setIsbnNo2(sellOrderRequest.get().getIsbnNo2());
				bookDTOObj.setIsbnType10(sellOrderRequest.get().getIsbnType10());
				bookDTOObj.setIsbnType13(sellOrderRequest.get().getIsbnType13());
				bookDTOObj.setPublishedDate(sellOrderRequest.get().getPublishedDate());
				bookDTOObj.setPublisher(sellOrderRequest.get().getPublisher());
				bookDTOObj.setQuantity(sellOrderRequest.get().getQuantity());
				bookDTOObj.setSmallThumbnail(sellOrderRequest.get().getSmallThumbnail());
				bookDTOObj.setThumbnail(sellOrderRequest.get().getThumbnail());
				
				bookService.saveBook(bookDTOObj);
			}
			
		}
		
		
		@GetMapping("/findBooks/{min}/{max}")
		public List<SellOrderRequest> getBook(@PathVariable(value = "min")int min,@PathVariable(value = "max")int max){
			return sellOrderRequestService.findBooks(min,max);
		}
		
		
		// form book table...............
		
//		@GetMapping("/findBooks/{min}/{max}")
//		public List<Book> getBook(@PathVariable(value = "min")int min,@PathVariable(value = "max")int max){
//			return bookService.findBooks(min,max);
//		}
		
		
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
		
		@RequestMapping("/fetchAuthor")
		public List<SellOrderRequest> findBookByAuthor(@RequestBody String author){
			return sellOrderRequestService.findBookByAuthor(author);
		}
		
		@RequestMapping("/fetchPublisher")
		public List<SellOrderRequest> findBookByPublisher(@RequestBody String publisher){
			return sellOrderRequestService.findBookByPublisher(publisher);
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
		@RequestMapping("/plusQuantity")
		public List<BuyOrderRequest> addQuantity(@RequestBody int requestBookId){
			return buyOrderRequestService.addQuantity(requestBookId);
		}
		
		@RequestMapping("/minusQuantity")
		public List<BuyOrderRequest> substractQuantity(@RequestBody int requestBookId){
			return buyOrderRequestService.minusQuantity(requestBookId);
		}
		
		@RequestMapping("/deleteBookRequest")
		public int deleteBookRequest(@RequestBody int requestBookId){
			buyOrderRequestService.deleteBookRequest(requestBookId);
			return 0;
		}
		
//		@RequestMapping("/addDeliverAddress")
//		public int addDeliveryAddress(@RequestBody int addressId){
//			int deliveryId=oldBookSellServices.getDeliveryPerson();
//			buyOrderRequestService.addDeliverAddress(addressId,deliveryId);
//			return 0;
//		}
		
//		@RequestMapping("/addDeliverAddressSingleBook")
//		public int addDeliveryAddressSingleBook(@RequestBody int array[]){
//			int addressID=array[0];
//			int bookId=array[1];
//			
//			Optional<SellOrderRequest> sellOrderRequest=sellOrderRequestService.findBookById(bookId);
//			BuyOrderRequestDTO buyOrderRequestDTO =new BuyOrderRequestDTO();
//			
//			buyOrderRequestDTO.setBookName(sellOrderRequest.get().getBookName());
//			buyOrderRequestDTO.setAuthors(sellOrderRequest.get().getAuthors());
//			buyOrderRequestDTO.setSmallThumbnail(sellOrderRequest.get().getSmallThumbnail());
//			buyOrderRequestDTO.setAmount(sellOrderRequest.get().getAmount());
//			buyOrderRequestDTO.setQuantity(sellOrderRequest.get().getQuantity());
//			buyOrderRequestDTO.setCheckStatus("pending");
//			buyOrderRequestDTO.setAddressId(addressID+"");
//			buyOrderRequestDTO.setBookId(sellOrderRequest.get().getSellOrderRequestId());
//
//			int deliveryId=oldBookSellServices.getDeliveryPerson();
//			buyOrderRequestDTO.setDileveryPersonId(deliveryId);
//
//			return buyOrderRequestService.saveRequest(buyOrderRequestDTO);
//		}
		
		@GetMapping("/getSellRequest")
		public Iterable<Object> getDeliverySellRequest() {
			int deliveryId=oldBookSellServices.getDeliveryPersonId();
			return buyOrderRequestService.deliverySellRequest(deliveryId);
		}
		
		@RequestMapping("/updateBuyBookStatus")
		public void updateBuyBookStatus(@RequestBody SellOrderRequestDTO sellOrderRequestDTO) {
			System.out.println(sellOrderRequestDTO.getSellOrderRequestId()+","+sellOrderRequestDTO.getCheck_status()+","+sellOrderRequestDTO.getFeedBack());
			buyOrderRequestService.updateBuyBookStatus(sellOrderRequestDTO.getSellOrderRequestId(),sellOrderRequestDTO.getCheck_status());
		}
		
		@RequestMapping("/fetchCategory")
		public List<SellOrderRequest> findBookByCategory(@RequestBody String category){
			return sellOrderRequestService.findBookByCategory(category);
		}
		
		@GetMapping("/getSellRequestAdmin")
		public Iterable<Object> getDeliverySellRequestAdmin() {
//			int deliveryId=oldBookSellServices.getDeliveryPersonId();
//			System.out.println(deliveryId+".............");
			return buyOrderRequestService.deliverySellRequestAdmin();
		}
		
		@GetMapping("/getRequestAdmin")
		public Iterable<Object> getDeliveryRequestAdmin() {
			int deliveryId=oldBookSellServices.getDeliveryPersonId();
			System.out.println(deliveryId+".............");
			return sellOrderRequestService.deliveryRequestAdmin();
		}
		
		@GetMapping("/buyHistory")
		public List<BuyOrderRequest> findBuyHistory(){
			
			return buyOrderRequestService.findBuyHistory();
		}
		
		@GetMapping("/sellHistory")
		public List<SellOrderRequest> findSellHistory(){
			return sellOrderRequestService.findSellHistory();
		}
		
		@GetMapping("/getBook")
		public List<SellOrderRequest> findBook(){
			return sellOrderRequestService.getAllBook();
		}
		
		@GetMapping("/getAllBook")
		public List<SellOrderRequest> findAllBook(){
			return sellOrderRequestService.getAllBook2();
		}
		
		@PostMapping("/updateBookPrice")
		public List<SellOrderRequest> updateBookPrice(@RequestBody int arr[]){
			
			return sellOrderRequestService.updateBookPrice(arr[0],arr[1]);
		}
		
		
		/* Controller for payment */
		
		@PostMapping("/charge")
	    public Charge chargeCard(@RequestBody int grandTotal, HttpServletRequest request) throws Exception {
	        String token = request.getHeader("token");
	        int amount = grandTotal;
	        return paymentService.chargeCreditCard(token, amount);
	    }
		
		@PostMapping("/savePayment")
		public Payment savePayment(@RequestBody PaymentDTO payment) {
			
//			System.out.println(payment.getAmount());
//			System.out.println(payment.getPaymentId());
//			System.out.println(payment.getCreated());
//			System.out.println(payment.getStatus());
//			System.out.println(payment.getTransactionId());
			System.out.println(payment.getAddressId());
			System.out.println(payment.getBookId());
			
			Optional<SellOrderRequest> sellOrderRequest=sellOrderRequestService.findBookById(payment.getBookId());
			BuyOrderRequestDTO buyOrderRequestDTO =new BuyOrderRequestDTO();
			
			buyOrderRequestDTO.setBookName(sellOrderRequest.get().getBookName());
			buyOrderRequestDTO.setAuthors(sellOrderRequest.get().getAuthors());
			buyOrderRequestDTO.setSmallThumbnail(sellOrderRequest.get().getSmallThumbnail());
			buyOrderRequestDTO.setAmount(sellOrderRequest.get().getAmount());
			buyOrderRequestDTO.setQuantity(sellOrderRequest.get().getQuantity());
			buyOrderRequestDTO.setCheckStatus("pending");
			buyOrderRequestDTO.setAddressId(payment.getAddressId()+"");
			buyOrderRequestDTO.setBookId(sellOrderRequest.get().getSellOrderRequestId());
			buyOrderRequestDTO.setStatus(payment.getStatus());
			buyOrderRequestDTO.setTransactionId(payment.getTransactionId());

			int deliveryId=oldBookSellServices.getDeliveryPerson();
			buyOrderRequestDTO.setDileveryPersonId(deliveryId);

			buyOrderRequestService.saveRequest(buyOrderRequestDTO);
	
			return paymentService.savePayment(payment);
		}
		
		@PostMapping("/saveMultipleBookPayment")
		public Payment saveMultipleBookPayment(@RequestBody PaymentDTO payment) {
			
			int deliveryId=oldBookSellServices.getDeliveryPerson();
			buyOrderRequestService.addDeliverAddress(payment.getAddressId(),deliveryId,payment.getStatus(),payment.getTransactionId());
			return paymentService.savePayment(payment);
		
		}
		
		
}
