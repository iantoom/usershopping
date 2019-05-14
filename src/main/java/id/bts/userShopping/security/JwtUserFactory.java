package id.bts.userShopping.security;

import id.bts.userShopping.domains.User;

public class JwtUserFactory {

	public static JwtUser create(User user) {
		
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
				user, null, true);
	}
}
