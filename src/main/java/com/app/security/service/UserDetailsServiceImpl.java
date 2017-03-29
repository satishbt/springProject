package com.app.security.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.stock.data.UserRepository;
import com.app.stock.model.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Users user = userRepository.findByEmail(username);
	if (null == user) {
	    throw new UsernameNotFoundException(username + "Not Found ");
	}

	return new CustomUserDetails(user);
    }

    private static class CustomUserDetails extends Users implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6587366830922011040L;

	public CustomUserDetails(Users user) {
	    super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	@Override
	public String getUsername() {
	    return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}

    }

}
