package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Dish;
import no.cmarker.backend.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 15:04.
 */
@Named
@SessionScoped
public class DishController {
	
	@Autowired private DishService dishService;
	@Autowired private EntityManager em;
	
	private String addDishName;
	private String addDishDescprition;
	
	public void createDish(){
		dishService.createDish(addDishName, addDishDescprition);
		setAddDishName("");
		setAddDishDescprition("");
	}
	
	public List<Dish> getAllDishes(){
		TypedQuery<Dish> query = em.createQuery("SELECT d FROM Dish d", Dish.class);
		return query.getResultList();
	}
	
	
	
	public String getAddDishName() {
		return addDishName;
	}
	
	public void setAddDishName(String addDishName) {
		this.addDishName = addDishName;
	}
	
	public String getAddDishDescprition() {
		return addDishDescprition;
	}
	
	public void setAddDishDescprition(String addDishDescprition) {
		this.addDishDescprition = addDishDescprition;
	}
}
