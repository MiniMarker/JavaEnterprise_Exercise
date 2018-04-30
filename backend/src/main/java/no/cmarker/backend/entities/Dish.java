package no.cmarker.backend.entities;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * @author Christian Marker on 30/04/2018 at 11:34.
 */
@Entity
public class Dish {
	@Id @GeneratedValue private Long id;
	@NotBlank @Size(max = 256) private String name;
	@NotBlank private String description;
	
	public Long getId() {
		return id;
	}
	/*
	public void setId(Long id) {
		this.id = id;
	}
	*/
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
