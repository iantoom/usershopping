package id.bts.userShopping.services;

import java.util.List;

import id.bts.userShopping.domains.User;

public interface UserService {

	User getUserByEmail(String email);
	
	User saveuser(User user);
	
	List<User> getAllUser();
	
	List<User> getAllUserPaged(int page);
}
