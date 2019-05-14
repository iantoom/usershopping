package id.bts.userShopping.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.bts.userShopping.domains.User;
import id.bts.userShopping.models.AuthResponse;
import id.bts.userShopping.security.JwtTokenUtil;
import id.bts.userShopping.security.JwtUser;
import id.bts.userShopping.security.JwtUserFactory;
import id.bts.userShopping.services.UserService;

@RestController
public class SignUpControllers {

	@Value("${jwt.header}")
	private String tokenHeader;
	private JwtTokenUtil jwtTokenUtil;
	private UserService userservice;

	public SignUpControllers(JwtTokenUtil jwtTokenUtil, UserService userservice) {
		super();
		this.jwtTokenUtil = jwtTokenUtil;
		this.userservice = userservice;
	}

	@PostMapping("api/users/signup")
	public ResponseEntity<AuthResponse> signup(@RequestBody User user) {
		final JwtUser userDetails = JwtUserFactory.create(user);
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		User userSaved = userservice.saveuser(user);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setEmail(userSaved.getEmail());
		authResponse.setToken(token);
		authResponse.setUsername(userSaved.getUsername());
		
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}
}
