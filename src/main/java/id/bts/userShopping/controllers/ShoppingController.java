package id.bts.userShopping.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import id.bts.userShopping.domains.Shopping;
import id.bts.userShopping.services.ShoppingService;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Shopping createShopping(@RequestBody Shopping shopping) {

		return shoppingService.createNewShopping(shopping);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<Shopping> getAllShopping() {
		return shoppingService.getAllShopping();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Shopping getSHoppingById(@PathVariable Long id) {
		return shoppingService.getShoppingById(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Shopping updateShopping(@PathVariable Long id, @RequestBody Shopping shopping) {
		return shoppingService.updateShopping(id, shopping);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteShoppingById (@PathVariable Long id) {
		shoppingService.deleteShopping(id);
	}
	
	@GetMapping(params = "page")
	@ResponseStatus(value = HttpStatus.OK)
	public List<Shopping> getAllPagedShopping (@RequestParam int page) {
		return shoppingService.getAllShoppingPaged(page);
	}
}
