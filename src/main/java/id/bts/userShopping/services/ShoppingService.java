package id.bts.userShopping.services;

import java.util.List;

import id.bts.userShopping.domains.Shopping;

public interface ShoppingService {

	public Shopping createNewShopping(Shopping shopping);
	
	List<Shopping> getAllShopping();
	
	Shopping getShoppingById(Long id);
	
	Shopping updateShopping(Long id, Shopping shopping);
	
	void deleteShopping(Long id);
	
	List<Shopping> getAllShoppingPaged(int page);
}
