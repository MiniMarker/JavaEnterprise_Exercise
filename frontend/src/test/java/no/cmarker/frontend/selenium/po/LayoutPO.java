package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author Christian Marker on 16/04/2018 at 15:30.
 */
public abstract class LayoutPO extends PageObject {
	
	public LayoutPO(WebDriver driver, String host, int port) {
		super(driver, host, port);
	}
	
	public LayoutPO(PageObject other) {
		super(other);
	}
	
	public void toStartingPage() {
		getDriver().get(host + ":" + port);
	}
	
	public boolean hasDishByName(String name){
		List<WebElement> elements = driver.findElements(
				By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]"));
		return ! elements.isEmpty();
	}
}
