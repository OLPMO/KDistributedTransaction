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

import com.alibaba.fastjson.JSON;


@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@GetMapping("/user/{id}")
	public Optional<User> findById(@PathVariable Integer id) {
		return this.userRepository.findById(id);
	}

	@GetMapping("/service/add/{name}")
	public User addViaName(@PathVariable String name) {

		return userService.addUserByName(name);
	}
	
	@GetMapping("/service/add/fail/{name}")
	public User addViaNameFail(@PathVariable String name) {

		return userService.addUserByNameFailed(name);
	}
	
	@GetMapping("/service/add/fail/noktx/{name}")
	public User addViaNameFailNotTx(@PathVariable String name) {

		return userService.addUserByNameFailedNotKDistTx(name);
	}
	
	@GetMapping("/user/all")
	public String getAllUsers() {
		List<User> users = this.userRepository.findAll();
		return JSON.toJSONString(users);
	}
	
	
}
