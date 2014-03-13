package se.leinonen.parser.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "drugimages_x")
public class DrugImages {

	@Id
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Image> images;

	public Long getId() {
		return id;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
