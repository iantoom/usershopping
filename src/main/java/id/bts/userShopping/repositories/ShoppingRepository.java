package id.bts.userShopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.bts.userShopping.domains.Shopping;

@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long>{

	
}
