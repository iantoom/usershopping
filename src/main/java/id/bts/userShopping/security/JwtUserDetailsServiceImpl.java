package id.bts.userShopping.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import id.bts.userShopping.domains.User;
import id.bts.userShopping.repositories.UserRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmailIgnoreCase(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Cannot found user with username: " + username);
		}else {
			return JwtUserFactory.create(user);
		}
	}
}
