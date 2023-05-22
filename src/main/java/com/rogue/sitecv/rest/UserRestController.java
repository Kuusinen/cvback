package com.rogue.sitecv.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rogue.sitecv.model.Role;
import com.rogue.sitecv.model.User;
import com.rogue.sitecv.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService service;

	@PostMapping()
	public void createUser(@RequestBody final User user) {
		service.saveUser(user);
	}

	@PostMapping(path = "/role")
	public void createRole(@RequestBody final Role role) {
		service.saveRole(role);
	}
}
