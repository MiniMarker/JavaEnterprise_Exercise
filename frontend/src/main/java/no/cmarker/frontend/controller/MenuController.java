package no.cmarker.frontend.controller;

import no.cmarker.backend.entities.Menu;
import no.cmarker.backend.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Christian Marker on 30/04/2018 at 15:27.
 */
@Named
@RequestScoped
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	private Menu displayedMenu;
	
	private String addMenuDate;
	private Map<Long, Boolean> checksForm = new HashMap<>();
	
	public String createMenu() {
		
		try {
			LocalDate date = LocalDate.parse(addMenuDate);
			
			List<Long> ids = checksForm.entrySet().stream()
					.filter(e -> e.getValue()) //fitrere ut alle som er true
					.map(e -> e.getKey()) //vise kun id
					.collect(Collectors.toList()); //legge inn id-ene i en liste
			
			menuService.createMenu(date, ids);
			return "/my_cantina.xhtml&faces-redirect=true";
		} catch (Exception e) {
			return "/my_cantina.xhtml&faces-redirect=true";
		}
	}
	
	
	
	public void goDefault(){
		displayedMenu = menuService.getTodaysMenu();
	}
	
	public void goNext(){
		displayedMenu = getNext();
	}
	
	public void goPrevious(){
		displayedMenu = getPrevious();
	}
	
	
	
	public Menu getCurrentMenu(){
		if (displayedMenu == null){
			goDefault();
		}
		return displayedMenu;
	}
	
	
	public Menu getNext(){
		Menu current = getCurrentMenu();
		
		if (current == null){
			return null;
		}
		return menuService.getNextMenu(current.getDate());
	}
	
	public Menu getPrevious(){
		Menu current = getCurrentMenu();
		
		if (current == null){
			return null;
		}
		return menuService.getPreviousMenu(current.getDate());
		
	}
	
	
	
	public String getAddMenuDate() {
		return addMenuDate;
	}
	
	public void setAddMenuDate(String addMenuDate) {
		this.addMenuDate = addMenuDate;
	}
	
	public Map<Long, Boolean> getChecksForm() {
		return checksForm;
	}
	
	public void setChecksForm(Map<Long, Boolean> checksForm) {
		this.checksForm = checksForm;
	}
}
