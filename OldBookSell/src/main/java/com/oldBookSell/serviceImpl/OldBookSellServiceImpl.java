package com.oldBookSell.serviceImpl;

import java.util.ArrayList;	



import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oldBookSell.dto.OldBookSellDTO;
import com.oldBookSell.model.Address;
import com.oldBookSell.model.UserDetails;
import com.oldBookSell.repository.UserDetailRepository;
import com.oldBookSell.service.OldBookSellServices;


@Service
public class OldBookSellServiceImpl implements OldBookSellServices{

	@Autowired
	UserDetailRepository userDetailRepository;
	
	@Autowired
    private JavaMailSender sender;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public OldBookSellDTO createUser(OldBookSellDTO odlBookSellDTO) {
		

		UserDetails userDetails=new UserDetails();
		
		userDetails.setFirstName(odlBookSellDTO.getFirstName());
		userDetails.setLastName(odlBookSellDTO.getLastName());
		userDetails.setMobileNumber(odlBookSellDTO.getMobileNumber());
		userDetails.setEmail(odlBookSellDTO.getEmail());
		userDetails.setPassword(bcryptEncoder.encode(odlBookSellDTO.getPassword()));
		userDetails.setRole(odlBookSellDTO.getRole());
		
		Address addressObj=new Address();
		
		addressObj.setAddress(odlBookSellDTO.getAddress());
		addressObj.setAddress2(odlBookSellDTO.getAddress2());
		addressObj.setDistrict(odlBookSellDTO.getDistrict());
		addressObj.setLocation(odlBookSellDTO.getLocation());
		addressObj.setPostalCode(odlBookSellDTO.getPinCode());
		addressObj.setState(odlBookSellDTO.getState());
		
		List<Address> list=new ArrayList<>();
		list.add(addressObj);
		
//		userDetails.setAddress(list);
		
		if(userDetailRepository.existsByEmail(odlBookSellDTO.getEmail())) {
			UserDetails userObj=	userDetailRepository.findByEmail(odlBookSellDTO.getEmail());
			list.addAll(userObj.getAddress());
			userObj.setAddress(list);
			userDetailRepository.save(userObj);
		}else {
			userDetails.setAddress(list);
			userDetailRepository.save(userDetails);
		}
		String msg="Greetings :) Welcome in OldBookHouse :)"
				+ " your UserName is your emial ";
		String result=sendMail(odlBookSellDTO.getEmail(),msg);
		System.out.println(result);
		return odlBookSellDTO;
	}

	@Override
	public UserDetails addAddress(OldBookSellDTO address) {
		
		Address addressObj=new Address();
		
		addressObj.setAddress(address.getAddress());
		addressObj.setAddress2(address.getAddress2());
		addressObj.setDistrict(address.getDistrict());
		addressObj.setLocation(address.getLocation());
		addressObj.setPostalCode(address.getPinCode());
		addressObj.setState(address.getState());
		
		List<Address> list=new ArrayList<>();
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails userObj=	userDetailRepository.findByEmail(authentication.getName());
//		System.out.println(userObj.getAddress().);
		
		list.addAll(userObj.getAddress());
		list.add(addressObj);
		userObj.setAddress(list);
		
		userDetailRepository.save(userObj);
		System.out.println("insert new record"+address.getAddress());
//		userDetailRepository.save(userDetails);
		return userDetailRepository.findByEmail(authentication.getName());
	}

	@Override
	public UserDetails getAddress() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userDetailRepository.findByEmail(authentication.getName());
	}

	@Override
	public String getRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		return userDetailRepository.hasRole(authentication.getName());
	}

	@Override
	public int getDeliveryPerson() {
		List<Integer> list= userDetailRepository.findAllByRole("deliveryPerson");
		int i=(int) (Math.random()*list.size());
		System.out.println(i+"........"+list);
		return list.get(i);
	}

	@Override
	public int getDeliveryPersonId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id=userDetailRepository.getDevileryPersonId(authentication.getName());
		return id;
	}

	@Override
	public Iterable<UserDetails> userList(){
//		LOGGER.info("OldBookSellService userList method is calling...");
		return userDetailRepository.findAll();
		
	}

	@Override
	public Optional<UserDetails> findById(int id) {
		return userDetailRepository.findById(id);
	}

	@Override
	public Optional<UserDetails> updateUser(UserDetails userDetails) {
		UserDetails user=userDetailRepository.findById(userDetails.getUserId()).get();
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		user.setMobileNumber(userDetails.getMobileNumber());
		user.setRole(userDetails.getRole());
		userDetailRepository.save(user);
		return Optional.of(user);
	}

	@Override
	public int deleteUser(int userId) {
		userDetailRepository.deleteById(userId);
		return 0;
	}
	
	@Override
	public String sendMail(String email,String msg) {
		
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(msg);
            helper.setSubject("OldBookHouse");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
		
	}

	@Override
	public void changePassword(String userName) {
		Random rand=new Random();
		String password=rand.nextInt(99999)+"";
		boolean result=userDetailRepository.existsByEmail(userName);
		if(result) {
			System.out.println(password);
			String msg="Greetings :) your password changed sucessfully 😄 "
					+ " your Password is "+password;
			sendMail(userName, msg);
			
			UserDetails userObj=	userDetailRepository.findByEmail(userName);
			userObj.setPassword(bcryptEncoder.encode(password));
			userDetailRepository.save(userObj);
			
		}
		
	}

}
