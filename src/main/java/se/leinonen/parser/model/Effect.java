package se.leinonen.parser.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "effects_x")
public class Effect {
	@Id
	private Long id;

	private String description;

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
