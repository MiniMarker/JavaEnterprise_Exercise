package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

/**
 * @author Christian Marker on 30/04/2018 at 18:02.
 */
public class DishesPO extends LayoutPO {
	
	public DishesPO(PageObject other) {
		super(other);
	}
	
	public DishesPO(WebDriver driver, String serverHost, int serverPort) {
		super(driver, serverHost, serverPort);
	}
	
	public IndexPO goToIndex(){
		
		clickAndWait("linkToHome");
		IndexPO indexPO = new IndexPO(this);
		assertTrue(indexPO.isOnPage());
		
		return indexPO;
	}
	
	public void createDish(String name, String description) {
		setText("createDishForm:addDishName", name);
		setText("createDishForm:addDishDescription", description);
		clickAndWait("createDishForm:addDishButton");
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Dishes");
	}
}

