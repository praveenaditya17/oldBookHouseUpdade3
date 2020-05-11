package com.oldBookSell.service;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oldBookSell.repository.UserDetailRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	public UserDetailRepository userDetailRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		  com.oldBookSell.model.UserDetails userDetail = userDetailRepository.findByEmail(username); 
		 
		  if (!userDetail.getEmail().equals(username)) { 
			  throw new UsernameNotFoundException("User not found with username: " + username); 
		  }
		
		  return new
		  org.springframework.security.core.userdetails.User(userDetail.getEmail(),
		  userDetail.getPassword(), new ArrayList<>());
		 

	}
}
