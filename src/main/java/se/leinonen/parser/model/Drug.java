package se.leinonen.parser.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name = "drugs")
public class Drug {
	@Id
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@Column(unique = true)
	private String simpleName;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String url;
	private String chemicalName;
	private String commonName;
	private String effects;

	private String imageUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	private DrugBasics basics;

	public DrugBasics getBasics() {
		return basics;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public String getCommonName() {
		return commonName;
	}

	public String getDescription() {
		return description;
	}

	public String getEffects() {
		return effects;
	}

	public Long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getName() {
		return name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public String getUrl() {
		return url;
	}

	public void setBasics(DrugBasics basics) {
		this.basics = basics;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
