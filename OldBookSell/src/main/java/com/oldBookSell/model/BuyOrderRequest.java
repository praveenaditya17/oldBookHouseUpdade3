package com.oldBookSell.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "buy_order_request")
@SequenceGenerator(name = "seq", initialValue = 20001, allocationSize = 1)
public class BuyOrderRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private int buyOrderRequestId;

	@Column(name = "book_name")
	private String bookName;// title

	@Column(name = "authors")
	private String authors;
	
	@Column(name = "smallThumbnail")
	private String smallThumbnail;// small image size

	@Column(name = "amount")
	private float amount;

	@Column(name = "quantity",columnDefinition = "integer default 1")
	private int quantity;

	@Column(name = "check_status")
	private String checkStatus;
	
	@Column(name="book_id")
	private int bookId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "address_id")
	private String addressId;
	
	@Column(name="dilevery_person_id")
	private int dileveryPersonId;

	public int getBuyOrderRequestId() {
		return buyOrderRequestId;
	}

	public void setBuyOrderRequestId(int buyOrderRequestId) {
		this.buyOrderRequestId = buyOrderRequestId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getSmallThumbnail() {
		return smallThumbnail;
	}

	public void setSmallThumbnail(String smallThumbnail) {
		this.smallThumbnail = smallThumbnail;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public int getDileveryPersonId() {
		return dileveryPersonId;
	}

	public void setDileveryPersonId(int dileveryPersonId) {
		this.dileveryPersonId = dileveryPersonId;
	}

	public BuyOrderRequest(int buyOrderRequestId, String bookName, String authors, String smallThumbnail, float amount,
			int quantity, String checkStatus, int bookId, String userId, String addressId, int dileveryPersonId) {
		super();
		this.buyOrderRequestId = buyOrderRequestId;
		this.bookName = bookName;
		this.authors = authors;
		this.smallThumbnail = smallThumbnail;
		this.amount = amount;
		this.quantity = quantity;
		this.checkStatus = checkStatus;
		this.bookId = bookId;
		this.userId = userId;
		this.addressId = addressId;
		this.dileveryPersonId = dileveryPersonId;
	}

	public BuyOrderRequest() {
		super();
	}

	
}
