package com.oldBookSell.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oldBookSell.model.UserDetails;

public interface UserDetailRepository extends CrudRepository<UserDetails,Integer>{
	UserDetails findByEmail(String name);
			boolean	existsByEmail(String name);
			
			@Query(value="select role from user_details where email=?1",nativeQuery = true)
			String hasRole(String name);
			
			@Query(value="select user_id from user_details where role=?1",nativeQuery = true)
			List<Integer> findAllByRole(String string);
			
			@Query(value="select user_id from user_details where email=?1",nativeQuery = true)
			int getDevileryPersonId(String name);
			
//			@Query(value="select user_id from user_details where email=?1",nativeQuery = true)
//			Iterable<Integer> getId(String email);
}
