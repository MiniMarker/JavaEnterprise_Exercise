package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 18:03.
 */
public class MenuPO extends LayoutPO {
	
	public MenuPO(PageObject other) {
		super(other);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("Menu");
	}
	
	
	public IndexPO createMenu(LocalDate date){
		
		setText("addMenuForm:addMenuDate", date.toString());
		
		clickAndWait("addMenuForm:addMenuButton");
		
		IndexPO indexPO = new IndexPO(getDriver(), getHost(), getPort());
		
		if (indexPO.isOnPage()){
			return indexPO;
		}
		
		return null;
		
	}
	
	private WebElement getCheckBoxForDish(String name){
		List<WebElement> elements = driver.findElements(
				By.xpath("//label[@class='dishName' and contains(text(),'"+name+"')]/parent::td/parent::tr//input"));
		
		return elements.get(0);
	}
	
	public void selectDishByName(String name, boolean on){
		
		WebElement check = getCheckBoxForDish(name);
		
		if(on != check.isSelected()){
			check.click();
		}
	}
	
	public boolean isDishSelected(String name){
		return getCheckBoxForDish(name).isSelected();
	}
	
}
