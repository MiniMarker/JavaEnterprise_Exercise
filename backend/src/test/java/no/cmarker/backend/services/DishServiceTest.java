package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import no.cmarker.backend.entities.Dish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Christian Marker on 30/04/2018 at 12:42.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DishServiceTest extends ServiceTestBase {
	
	@Autowired
	private DishService dishService;
	
	@Test
	public void testCreateDish(){
		
		String name = "NotGandalf";
		String desc = "I shall pass!";
		
		Long id = dishService.createDish(name, desc);
		
		assertNotNull(id);
	}
	
	@Test
	public void testCreateTwoDishes(){
		
		String dish1Name = "NotGandalf";
		String dish1Desc = "I shall pass!";
		
		String dish2Name = "Bilbo";
		String dish2Desc = "I'm going on an adventure!";
		
		Long dish1Id = dishService.createDish(dish1Name, dish1Desc);
		Long dish2Id = dishService.createDish(dish2Name, dish2Desc);
		
		assertNotNull(dish1Id);
		assertNotNull(dish2Id);
		assertNotEquals(dish1Id, dish2Id);
		assertEquals(2, dishService.getAllDishes().size());
	}
	
}
