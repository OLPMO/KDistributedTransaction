package com.test.service;

import com.kdt.client.annotation.KDistributedTransaction;
import com.test.entity.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@KDistributedTransaction(isFinalTx = true)
	public User addUserByName(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);
		return newUser;
	}
	
	@Transactional
	@KDistributedTransaction(isFinalTx = true)
	public User addUserByNameFailed(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);

		int a = 1/0;
		return newUser;
	}

	@Transactional
	public User addUserByNameFailedNotKDistTx(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);
		int a = 1/0;
		return newUser;
	}
}
