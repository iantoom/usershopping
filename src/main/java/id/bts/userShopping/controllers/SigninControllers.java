package id.bts.userShopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.bts.userShopping.domains.User;
import id.bts.userShopping.models.AuthResponse;
import id.bts.userShopping.security.JwtTokenUtil;
import id.bts.userShopping.security.JwtUser;
import id.bts.userShopping.security.JwtUserFactory;

@RestController
public class SigninControllers {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/api/users/signin")
	public AuthResponse signin (@RequestBody User user) {
		final JwtUser userDetails = JwtUserFactory.create(user);
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setEmail(user.getEmail());
		authResponse.setToken(token);
		authResponse.setUsername(user.getUsername());
		return authResponse;
	}
}
