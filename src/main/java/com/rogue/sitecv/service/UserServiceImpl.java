package com.rogue.sitecv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rogue.sitecv.dao.RoleRepository;
import com.rogue.sitecv.dao.UserRepository;
import com.rogue.sitecv.model.Role;
import com.rogue.sitecv.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder cryptPasswordEncoder;

	@Override
	public User saveUser(User user) {

		updateUserWithGoodRole(user);

		user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	private void updateUserWithGoodRole(final User user) {
		final Optional<Role> roleFind = roleRepository.findByName(user.getRole().getName());

		if (roleFind.isPresent()) {
			user.setRole(roleFind.get());
		} else {
			roleRepository.save(user.getRole());
		}
	}

	@Override
	public User findUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

}
