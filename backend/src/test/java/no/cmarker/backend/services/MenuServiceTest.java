package no.cmarker.backend.services;

import no.cmarker.backend.StubApplication;
import no.cmarker.backend.entities.Dish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Christian Marker on 30/04/2018 at 12:42.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MenuServiceTest extends ServiceTestBase {
	
	@Autowired DishService dishService;
	@Autowired MenuService menuService;
	
	@Test(expected = NullPointerException.class)
	public void testCreateMenuWithNoDish(){
		
		LocalDate today = LocalDate.now();
		
		menuService.createMenu(today, null);
	}
	
	@Test
	public void getCurrentDish(){
		
		LocalDate today = LocalDate.now();
		List<Long> dishList = new ArrayList<>();
		
		//create dishes
		Long dishId = dishService.createDish("Foo", "Bar");
		dishList.add(dishId);
		
		//create menus
		Long menuId = menuService.createMenu(today, dishList);
		
		//tests
		assertNotNull(menuId);
		assertEquals(menuService.getTodaysMenu().getId(), menuId);
	}
	
	@Test
	public void testGetAbsentPreviousMenu(){
		
		LocalDate today = LocalDate.now();
		List<Long> dishList = new ArrayList<>();
		
		//create dishes
		Long dishId = dishService.createDish("Foo", "Bar");
		dishList.add(dishId);
		
		//create menus
		Long menuId = menuService.createMenu(today, dishList);
		
		//tests
		assertNotNull(menuId);
		assertNull(menuService.getPreviousMenu(today));
	}
	
	@Test
	public void testGetAbsentNextMenu(){
		
		LocalDate today = LocalDate.now();
		List<Long> dishList = new ArrayList<>();
		
		//create dishes
		Long dishId = dishService.createDish("Foo", "Bar");
		dishList.add(dishId);
		
		//create menus
		Long menuId = menuService.createMenu(today, dishList);
		
		//tests
		assertNotNull(menuId);
		assertNull(menuService.getNextMenu(today));
	}
	
	@Test
	public void testGetPreviousMenu(){
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		
		List<Long> dishList = new ArrayList<>();
		
		//create dishes
		Long dishId = dishService.createDish("Foo", "Bar");
		dishList.add(dishId);
		
		//create menus
		Long menuId = menuService.createMenu(yesterday, dishList);
		
		//tests
		assertNotNull(menuId);
		assertEquals(menuService.getPreviousMenu(today).getId(), menuId);
	}
	
	@Test
	public void testThreeMenus(){
		
		List<Long> dishList = new ArrayList<>();
		Long dishId = dishService.createDish("Foo", "Bar");
		dishList.add(dishId);
		
		//today
		LocalDate today = LocalDate.now();
		Long todayMenuId = menuService.createMenu(today, dishList);
		
		//yesterday
		LocalDate yesterday = today.minusDays(1);
		Long yesterdayMenuId = menuService.createMenu(yesterday, dishList);
		
		//tomorrow
		LocalDate tomorrow = today.plusDays(1);
		Long tomorrowMenuId = menuService.createMenu(tomorrow, dishList);
		
		//today
		assertEquals(menuService.getNextMenu(today).getId(), tomorrowMenuId);
		assertEquals(menuService.getPreviousMenu(today).getId(), yesterdayMenuId);
		
		//yesterday
		assertEquals(menuService.getNextMenu(yesterday).getId(), todayMenuId);
		assertNull(menuService.getPreviousMenu(yesterday));
		
		//tomorrow
		assertEquals(menuService.getPreviousMenu(tomorrow).getId(), todayMenuId);
		assertNull(menuService.getNextMenu(tomorrow));
		
	}
	
	
	
}
