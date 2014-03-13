package se.leinonen.parser.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "effects")
public class DrugEffects {
	@Id
	private Long id;

	// div.effects-list div.effects-list-positive ul.effects-item
	@OneToMany(cascade = CascadeType.ALL)
	private List<Effect> positive;

	// div.effects-list div.effects-list-negative ul.effects-item
	@OneToMany(cascade = CascadeType.ALL)
	private List<Effect> negative;

	// div.effects-list div.effects-list-neutral ul.effects-item
	@OneToMany(cascade = CascadeType.ALL)
	private List<Effect> neutral;

	// img.duration-chart
	private String durationChartUrl;

	// div.effects-description div.effects-text
	private String description;

	public Long getId() {
		return id;
	}

	public List<Effect> getNegative() {
		return negative;
	}

	public List<Effect> getNeutral() {
		return neutral;
	}

	public List<Effect> getPositive() {
		return positive;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
