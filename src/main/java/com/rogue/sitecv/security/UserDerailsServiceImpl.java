package com.rogue.sitecv.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rogue.sitecv.model.User;
import com.rogue.sitecv.service.UserService;

@Service
public class UserDerailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = userService.findUserByName(username);

		if( user == null) {
			throw new UsernameNotFoundException("Unable to Load user : " + username);
		}
		
		final List<GrantedAuthority> authority = new ArrayList<>();

		authority.add(new SimpleGrantedAuthority(user.getRole().getName()));

		final UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getName(),
				user.getPassword(), authority);
		
		return userDetails;
	}

}
