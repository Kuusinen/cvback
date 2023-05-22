package com.rogue.sitecv.service;

import com.rogue.sitecv.model.Role;
import com.rogue.sitecv.model.User;

public interface UserService {

	User saveUser(User user);

	User findUserByName(String name);

	Role saveRole(Role role);
}
