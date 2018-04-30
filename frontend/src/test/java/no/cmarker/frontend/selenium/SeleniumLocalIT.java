package no.cmarker.frontend.selenium;

import no.cmarker.Application;
import no.cmarker.frontend.selenium.po.DishesPO;
import no.cmarker.frontend.selenium.po.IndexPO;
import no.cmarker.frontend.selenium.po.MenuPO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Christian Marker on 16/04/2018 at 15:26.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SeleniumLocalIT {
	
	@LocalServerPort private int port;
	private static WebDriver driver;
	
	private IndexPO home;
	
	/*
		BEFORE TESTS
	 */
	@BeforeClass
	public static void initClass(){
		
		driver = SeleniumDriverHandler.getChromeDriver();
		
		if(driver == null){
			//Do not fail the tests.
			throw new AssumptionViolatedException("Cannot find/initialize Chrome driver");
		}
	}
	
	@Before
	public void initTest() {
		
		getDriver().manage().deleteAllCookies();
		
		home = new IndexPO(getDriver(), getServerHost(), getServerPort());
		
		home.toStartingPage();
		
		assertTrue("Failed to start from Home Page", home.isOnPage());
	}
	
	/*
		AFTER TESTS
	 */
	
	@AfterClass
	public static void tearDown() {
		if(driver != null) {
			driver.close();
		}
	}
	
	protected WebDriver getDriver() {
		return driver;
	}
	
	protected String getServerHost() {
		return "localhost";
	}
	
	protected int getServerPort() {
		return port;
	}
	
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	private String getUniqueDishName(){
		return "Dish_" + counter.getAndIncrement();
	}
	
	private String createNewUniqueDish(){
		home.toStartingPage();
		assertTrue(home.isOnPage());
		
		DishesPO dishesPO = home.goToDishes();
		assertTrue(dishesPO.isOnPage());
		
		String uniqueName = getUniqueDishName();
		
		assertFalse(dishesPO.hasDishByName(uniqueName));
		
		dishesPO.createDish(uniqueName, "foo");
		
		assertTrue(dishesPO.hasDishByName(uniqueName));
		
		return uniqueName;
	}
	
	
	
	/*
		Tests
	 */
	
	@Test
	public void goToStartPage(){
		home.toStartingPage();
		assertTrue(home.isOnPage());
	}
	
	@Test
	public void testCreateDish(){
		home.toStartingPage();
		DishesPO dishesPO = home.goToDishes();
		assertTrue(dishesPO.isOnPage());
		
		String unique = getUniqueDishName();
		
		assertFalse(dishesPO.hasDishByName(unique));
		
		dishesPO.createDish(unique, "foo");
		
		assertTrue(dishesPO.hasDishByName(unique));
	}
	
	@Test
	public void testMenu(){
		
		home.toStartingPage();
		DishesPO dishesPO = home.goToDishes();
		assertTrue(dishesPO.isOnPage());
		
		String dish1 = createNewUniqueDish();
		String dish2 = createNewUniqueDish();
		String dish3 = createNewUniqueDish();
		
		home.toStartingPage();
		
		MenuPO menuPO = home.goToMenus();
		assertTrue(menuPO.hasDishByName(dish1));
		assertTrue(menuPO.hasDishByName(dish2));
		assertTrue(menuPO.hasDishByName(dish3));
		
		assertFalse(menuPO.isDishSelected(dish1));
		assertFalse(menuPO.isDishSelected(dish2));
		assertFalse(menuPO.isDishSelected(dish3));
		
		menuPO.selectDishByName(dish1, false);
		menuPO.selectDishByName(dish2, true);
		menuPO.selectDishByName(dish3, true);
		
		LocalDate today = LocalDate.now();
		
		menuPO.createMenu(today);
		
		home.clickAndWait("defultButtonForm:defaultButton");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(home.isOnPage());
		assertFalse(home.hasDishByName(dish1));
		assertTrue(home.hasDishByName(dish2));
		assertTrue(home.hasDishByName(dish3));
	}
	
	@Test
	public void testDifferentDates(){
		home.toStartingPage();
		DishesPO dishesPO = home.goToDishes();
		assertTrue(dishesPO.isOnPage());
		
		String dish1 = createNewUniqueDish();
		String dish2 = createNewUniqueDish();
		String dish3 = createNewUniqueDish();
		
		home.toStartingPage();
		
		MenuPO menuPO = home.goToMenus();
		assertTrue(menuPO.hasDishByName(dish1));
		assertTrue(menuPO.hasDishByName(dish2));
		assertTrue(menuPO.hasDishByName(dish3));
		
		assertFalse(menuPO.isDishSelected(dish1));
		assertFalse(menuPO.isDishSelected(dish2));
		assertFalse(menuPO.isDishSelected(dish3));
		
		menuPO.selectDishByName(dish1, false);
		menuPO.selectDishByName(dish2, true);
		menuPO.selectDishByName(dish3, true);
		
		LocalDate today = LocalDate.now();
		menuPO.createMenu(today);
		assertTrue(home.isOnPage());
		
		home.goToMenus();
		menuPO.selectDishByName(dish1, true);
		menuPO.selectDishByName(dish2, true);
		menuPO.selectDishByName(dish3, false);
		
		LocalDate tomorrow = today.plusDays(1);
		menuPO.createMenu(tomorrow);
		assertTrue(home.isOnPage());
		
		home.goToMenus();
		menuPO.selectDishByName(dish1, true);
		menuPO.selectDishByName(dish2, false);
		menuPO.selectDishByName(dish3, true);
		
		LocalDate yesterday = today.minusDays(1);
		menuPO.createMenu(yesterday);
		assertTrue(home.isOnPage());
		
		home.clickAndWait("defultButtonForm:defaultButton");
		assertFalse(home.hasDishByName(dish1));
		assertTrue(home.hasDishByName(dish2));
		assertTrue(home.hasDishByName(dish3));
		
		home.clickAndWait("nextButtonForm:nextButton");
		assertTrue(home.hasDishByName(dish1));
		assertTrue(home.hasDishByName(dish2));
		assertFalse(home.hasDishByName(dish3));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		home.clickAndWait("previousButtonForm:previousButton");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(home.hasDishByName(dish1));
		assertFalse(home.hasDishByName(dish2));
		assertTrue(home.hasDishByName(dish3));
		
	}
	
}
