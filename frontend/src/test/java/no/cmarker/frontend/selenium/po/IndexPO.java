package no.cmarker.frontend.selenium.po;

import no.cmarker.frontend.selenium.PageObject;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Christian Marker on 16/04/2018 at 15:27.
 */
public class IndexPO extends LayoutPO {
	
	public IndexPO(PageObject other) {
		super(other);
	}
	
	public IndexPO(WebDriver driver, String serverHost, int serverPort) {
		super(driver, serverHost, serverPort);
	}
	
	@Override
	public boolean isOnPage() {
		return getDriver().getTitle().contains("MyCantina");
	}
	
	public DishesPO goToDishes() {
		
		clickAndWait("linkToDishes");
		DishesPO dishesPO = new DishesPO(this);
		assertTrue(dishesPO.isOnPage());
		
		return dishesPO;
	}
	
	public MenuPO goToMenus() {
		
		clickAndWait("linkToMenu");
		MenuPO menuPO = new MenuPO(this);
		assertTrue(menuPO.isOnPage());
		
		return menuPO;
	}
}
