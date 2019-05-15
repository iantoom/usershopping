package id.bts.userShopping.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.bts.userShopping.domains.User;
import id.bts.userShopping.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		
		return userRepository.findByEmailIgnoreCase(email);
	}

	@Override
	public User saveuser(User user) {
		
		User userSaved = user;
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		userSaved.setPassword(encodedPassword);
		
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public List<User> getAllUserPaged(int page) {
		
		Page <User> PagedUsers = userRepository.findAll(PageRequest.of(page, 2));
		List <User> users = new ArrayList<User>();
		
		PagedUsers.stream().forEach(user -> users.add(user));
		
		return users;
	}
	
	

}
