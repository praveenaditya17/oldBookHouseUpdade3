package com.oldBookSell.service;

import java.util.Optional;


import com.oldBookSell.dto.OldBookSellDTO;
import com.oldBookSell.model.UserDetails;

public interface OldBookSellServices {
	public OldBookSellDTO createUser(OldBookSellDTO userDetail);
	public UserDetails addAddress(OldBookSellDTO address);
	public UserDetails getAddress();
	public String getRole();
	public int getDeliveryPerson();
	public int getDeliveryPersonId();
	public Iterable<UserDetails> userList();
	public Optional<UserDetails> findById(int id);
	public Optional<UserDetails> updateUser(UserDetails user);
	public int deleteUser(int userId);
	public void changePassword(String userName);
	public String sendMail(String email, String msg);
}
