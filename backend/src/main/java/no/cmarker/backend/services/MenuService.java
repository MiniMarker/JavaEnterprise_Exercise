package no.cmarker.backend.services;

import no.cmarker.backend.entities.Dish;
import no.cmarker.backend.entities.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Christian Marker on 30/04/2018 at 12:01.
 */
@Service
@Transactional
public class MenuService {
	
	@Autowired
	private EntityManager em;
	
	public Long createMenu(LocalDate date, List<Long> dishIds){
		
		Menu menu;
		
		//Siden date er unik så må vi sjekke om det finnes en eksisterene meny for dagen vi ønsker å legge inn på
		//Hvis det ikke finnes noen på denne datoen så opprettes det en ny på denne datoen,
		//ellers så returnerer vi den og klarerer listen over dishes
		TypedQuery<Menu> query = em.createQuery("SELECT m FROM Menu m WHERE m.date = :date", Menu.class);
		query.setParameter("date", date);
		
		List<Menu> resultList = query.getResultList();
		
		if (resultList.isEmpty()){
			menu = new Menu();
			menu.setDate(date);
		} else {
			menu = resultList.get(0);
		}
		
		menu.getDishes().clear();
		
		//vi må iterere over listen over id'er som blir gitt som parameter,
		//disse idene skal tilhøre dishes som ligger i databasen. Hvis ikke så skal det kastes ett exception
		dishIds.forEach((id) -> {
			Dish dish = em.find(Dish.class, id);
			
			if (dish == null){
				throw new IllegalArgumentException("Invalid id for dish");
			}
			
			menu.getDishes().add(dish);
		});
		
		//hvis det be opprettet ett nytt element så skal denen sendes til databasen
		//hvis vi oppdaterer dataen i ett eksisterende element så returnerer vi bare iden til det elementet
		if (menu.getId() == null){
			em.persist(menu);
		}
		
		return menu.getId();
	}
	
	public Menu getTodaysMenu(){
		
		LocalDate today = LocalDate.now();
		
		TypedQuery<Menu> query = em.createQuery("SELECT m FROM Menu m WHERE m.date >= :date ORDER BY m.date", Menu.class);
		query.setParameter("date", today);
		query.setMaxResults(1);
		
		List<Menu> resultList = query.getResultList();
		
		if (!resultList.isEmpty()){
			return resultList.get(0);
		}
		
		return getPreviousMenu(LocalDate.now());
	}
	
	public Menu getPreviousMenu(LocalDate date) {
		
		TypedQuery<Menu> query = em.createQuery("SELECT m FROM Menu m WHERE m.date < :date ORDER BY m.date DESC", Menu.class);
		query.setParameter("date", date);
		query.setMaxResults(1);
		
		List<Menu> resultList = query.getResultList();
		
		if (!resultList.isEmpty()){
			return resultList.get(0);
		}
		
		return null;
	}
	
	public Menu getNextMenu(LocalDate date) {
		
		TypedQuery<Menu> query = em.createQuery("SELECT m FROM Menu m WHERE m.date > :date ORDER BY m.date", Menu.class);
		query.setParameter("date", date);
		query.setMaxResults(1);
		
		List<Menu> resultList = query.getResultList();
		
		if (!resultList.isEmpty()){
			return resultList.get(0);
		}
		
		return null;
		
	}
	
}
