package no.cmarker.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.function.Supplier;

/**
 * @author Christian Marker on 16/04/2018 at 17:51.
 */

@Service
public class DefaultDataInitializerService {
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private MenuService menuService;
	
	@PostConstruct
	public void initialize(){
		/*
		Long id1 = attempt(() -> dishService.createDish("Pizza", "Italian"));
		Long id2 = attempt(() -> dishService.createDish("Soup", "Tomato"));
		Long id3 = attempt(() -> dishService.createDish("Salad", "Ceasar"));
		Long id4 = attempt(() -> dishService.createDish("Hot Dogs", "With cheese"));
		*/
	}
	
	private <T> T attempt(Supplier<T> lambda) {
		try {
			return lambda.get();
		} catch (Exception e) {
			return null;
		}
	}
}
