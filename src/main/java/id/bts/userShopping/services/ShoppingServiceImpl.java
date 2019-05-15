package id.bts.userShopping.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import id.bts.userShopping.domains.Shopping;
import id.bts.userShopping.repositories.ShoppingRepository;

@Service
public class ShoppingServiceImpl implements ShoppingService{

	@Autowired
	private ShoppingRepository shoppingRepository;
	
	public Shopping createNewShopping(Shopping shopping) {
		
		return shoppingRepository.save(shopping);
	}

	@Override
	public List<Shopping> getAllShopping() {
		return shoppingRepository.findAll();
	}

	@Override
	public Shopping getShoppingById(Long id) {
		return shoppingRepository.findById(id).get();
	}

	@Override
	public Shopping updateShopping(Long id, Shopping shopping) {
		Optional<Shopping> shoppingOpt = shoppingRepository.findById(id);
		
		Shopping shoppingReady = shoppingOpt.get();
		
		if (shoppingOpt.isEmpty()) {
			shoppingReady = new Shopping();
		}
		
		shoppingReady.setCreateddate(shopping.getCreateddate());
		shoppingReady.setName(shopping.getName());
		
		return shoppingRepository.save(shoppingReady);
	}

	@Override
	public void deleteShopping(Long id) {
		shoppingRepository.deleteById(id);
	}

	@Override
	public List<Shopping> getAllShoppingPaged(int page) {
		
		Page<Shopping> PagedShopping = shoppingRepository.findAll(PageRequest.of(page, 2));
		List<Shopping> shoppings = new ArrayList<Shopping>();
		
		PagedShopping.stream().forEach(shopping -> shoppings.add(shopping));
		return shoppings;
	}
	
}
