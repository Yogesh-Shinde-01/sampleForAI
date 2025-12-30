package com.practice_security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice_security.entities.User;
import com.practice_security.services.UserService;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

}