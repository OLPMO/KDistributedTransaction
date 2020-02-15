package com.test.service;

import com.kdt.client.annotation.KDistributedTransaction;
import com.test.entity.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@KDistributedTransaction
	public User testDistTxFailed(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);
		try{
			this.restTemplate.getForObject("http://127.0.0.1:9001/service/add/fail/"
					+ name+"2fail", User.class);
		}catch (Exception ignored){

		}

		return newUser;
	}

	@Transactional
	@KDistributedTransaction
	public User testDistTxSucceed(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);
		try{
			this.restTemplate.getForObject("http://127.0.0.1:9001/service/add/"
					+ name+"2success", User.class);
		}catch (Exception ignored){

		}

		return newUser;
	}

	@Transactional
	public User testNoDistTxFailed(String name) {
		User user = new User();
		user.setUSE_NAME(name);
		user.setUSE_AGE(10);
		User newUser = this.userRepository.save(user);
		try{
			this.restTemplate.getForObject("http://127.0.0.1:9001/service/add/fail/noktx/"
					+ name+"2fail-notkdisttx", User.class);
		}catch (Exception ignored){

		}

		return newUser;
	}
}
