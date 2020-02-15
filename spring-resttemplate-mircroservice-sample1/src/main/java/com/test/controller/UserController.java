package com.test.controller;

import java.util.List;
import java.util.Optional;

import com.test.entity.User;
import com.test.repository.UserRepository;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/user/{id}")
	public Optional<User> findById(@PathVariable Integer id) {
		return this.userRepository.findById(id);
	}

	@GetMapping("/service/add/{name}")
	public User addServiceViaName(@PathVariable String name) {
		return this.restTemplate.getForObject("http://127.0.0.1:9001/user/add/"
				+ name, User.class);
	}
	
	@GetMapping("/user/add/{name}")
	public User addViaName(@PathVariable String name) {

		return userService.testDistTxSucceed(name);
	}
	
	@GetMapping("/user/add/fail/{name}")
	public User addViaNameFail(@PathVariable String name) {
		return userService.testDistTxFailed(name);
	}

	@GetMapping("/user/add/fail/noktx/{name}")
	public User addViaNameFailNoKTx(@PathVariable String name) {
		return userService.testNoDistTxFailed(name);
	}

	@GetMapping("/user/all")
	public String getAllUsers() {
		List<User> users = this.userRepository.findAll();
		return JSON.toJSONString(users);
	}
}
