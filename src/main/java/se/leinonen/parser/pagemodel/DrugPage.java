package se.leinonen.parser.pagemodel;

import org.jsoup.nodes.Document;
import se.leinonen.parser.ErowidUrl;
import se.leinonen.parser.model.Drug;

import java.util.ArrayList;
import java.util.List;

public class DrugPage extends Page {

	private String description;
	private String chemicalName;
	private String substanceName;
	private String effects;
	private String commonName;

	private String imageUrl;

	public DrugPage(final ErowidUrl url, final Document doc) {
		super(url, doc);
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

	public String getImageUrl() {
		return imageUrl;
	}

	public List<ErowidUrl> getLinksForType(ErowidUrl.Type type) {
		List<ErowidUrl> list = new ArrayList<ErowidUrl>();
		for (ErowidUrl url : getLinks()) {
			if (url.getType() == type) {
				list.add(url);
			}
		}
		return list;
	}

	public String getSubstanceName() {
		return substanceName;
	}

	@Override
	public Page process() {
		super.process();

		String substanceName = parseText("div.ts-substance-name");
		if ("".equals(substanceName)) {
			// Probably a plant, try extracting Genus..
			substanceName = parseText("div.genus");
		}
		setSubstanceName(substanceName);
		setChemicalName(parseText("div.sum-chem-name"));
		setDescription(parseText("div.sum-description"));
		setEffects(parseText("div.sum-effects"));
		setCommonName(parseText("div.sum-common-name"));

		String image = parseImage("div.summary-card-topic-image img");
		String imageUrl = getUrl().getBaseUrl() + "/" + getUrl().getPath() + "/" + image;
		setImageUrl(imageUrl);

		return this;
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

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setSubstanceName(String extractText) {
		this.substanceName = extractText;
	}

	public Drug toDrug() {
		String substanceName = this.getSubstanceName();
		if (substanceName == null && !"".equals(substanceName))
			return null;

		Drug drug = new Drug();
		drug.setSimpleName(getUrl().getSimpleName());
		drug.setName(substanceName);
		drug.setDescription(this.getDescription());
		drug.setChemicalName(this.getChemicalName());
		drug.setEffects(this.getEffects());
		drug.setCommonName(this.getCommonName());
		drug.setUrl(this.getUrl().getUrl());
		drug.setImageUrl(this.getImageUrl());

		return drug;
	}
}
