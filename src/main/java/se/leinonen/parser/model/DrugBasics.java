package se.leinonen.parser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "basics")
public class DrugBasics {
	@Id
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String dose;

	@Column(columnDefinition = "TEXT")
	private String price;

    @Column(columnDefinition = "TEXT")
	private String law;

    @Column(columnDefinition = "TEXT")
	private String chemistry;

    @Column(columnDefinition = "TEXT")
	private String pharmacology;

    @Column(columnDefinition = "TEXT")
	private String production;

    @Column(columnDefinition = "TEXT")
	private String history;

    @Column(columnDefinition = "TEXT")
	private String effects;

    @Column(columnDefinition = "TEXT")
	private String onset;

    @Column(columnDefinition = "TEXT")
	private String duration;

    @Column(columnDefinition = "TEXT")
	private String addiction;

	public String getAddiction() {
		return addiction;
	}

	public String getChemistry() {
		return chemistry;
	}

	public String getDose() {
		return dose;
	}

	public String getDuration() {
		return duration;
	}

	public String getEffects() {
		return effects;
	}

	public String getHistory() {
		return history;
	}

	public Long getId() {
		return id;
	}

	public String getLaw() {
		return law;
	}

	public String getOnset() {
		return onset;
	}

	public String getPharmacology() {
		return pharmacology;
	}

	public String getPrice() {
		return price;
	}

	public String getProduction() {
		return production;
	}

	public void setAddiction(String addiction) {
		this.addiction = addiction;
	}

	public void setChemistry(String chemistry) {
		this.chemistry = chemistry;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLaw(String law) {
		this.law = law;
	}

	public void setOnset(String onset) {
		this.onset = onset;
	}

	public void setPharmacology(String pharmacology) {
		this.pharmacology = pharmacology;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setProduction(String production) {
		this.production = production;
	}
}
