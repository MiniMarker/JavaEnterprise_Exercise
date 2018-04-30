package no.cmarker.backend.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Christian Marker on 30/04/2018 at 11:44.
 */
@Entity
public class Menu {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	@NotNull
	private LocalDate date;
	
	@NotNull
	@Size(min = 1)
	@ManyToMany(fetch = EAGER, cascade = ALL)
	private List<Dish> dishes = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	/*
	public void setId(Long id) {
		this.id = id;
	}*/
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public List<Dish> getDishes() {
		return dishes;
	}
	/*
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
	*/
}
