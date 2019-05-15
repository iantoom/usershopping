package id.bts.userShopping.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import id.bts.userShopping.domains.User;
import id.bts.userShopping.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/api/users/")
	@ResponseStatus(code = HttpStatus.OK)
	public List<User> getAllUsers() {
		
		return userService.getAllUser();
	}
	
	@GetMapping(path = "/api/users/", params = {"page"})
	public List<User> getAllUserPaged(@RequestParam int page) {
		
		return userService.getAllUserPaged(page);
	}
}
