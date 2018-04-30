package no.cmarker.backend.services;

import no.cmarker.backend.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 11:51.
 */
@Service
@Transactional
public class DishService {
	
	@Autowired
	private EntityManager em;
	
	public Long createDish(String name, String description){
		
		Dish dish = new Dish();
		dish.setName(name);
		dish.setDescription(description);
		
		em.persist(dish);
		
		return dish.getId();
	}
	
	public List<Dish> getAllDishes(){
		
		TypedQuery<Dish> query = em.createQuery("SELECT d FROM Dish d", Dish.class);
		
		//når tilkoblingen til dishes er "fetchType = eager" så trengs ikke det ikke å forceload
		return query.getResultList();
		
	}
	
}
