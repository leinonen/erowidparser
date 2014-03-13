package se.leinonen.parser.pagemodel;

import org.jsoup.nodes.Document;

import se.leinonen.parser.ErowidUrl;
import se.leinonen.parser.model.DrugBasics;

public class BasicsPage extends Page {
    private String dose;
    private String price;
    private String law;
    private String chemistry;
    private String pharmacology;
    private String production;
    private String history;
    private String effects;
    private String onset;
    private String duration;
    private String addiction;

    public BasicsPage(final ErowidUrl url, final Document doc) {
        super(url, doc);
    }

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

    private String extractTextFromDiv(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("div.basics-").append(name).append(" div.basics-text");
        return fixSummary(parseText(sb.toString()));
    }

    @Override
    public Page process() {
        super.process();

        setDose(extractTextFromDiv("dose"));
        setPrice(extractTextFromDiv("price"));
        setLaw(extractTextFromDiv("law"));
        setChemistry(extractTextFromDiv("chemistry"));
        setPharmacology(extractTextFromDiv("pharmacology"));
        setProduction(extractTextFromDiv("production"));
        setHistory(extractTextFromDiv("history"));
        setEffects(extractTextFromDiv("effects"));
        setOnset(extractTextFromDiv("onset"));
        setDuration(extractTextFromDiv("duration"));
        setAddiction(extractTextFromDiv("addiction"));

        return this;
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

    public DrugBasics toDrugBasics() {
        DrugBasics basics = new DrugBasics();
        basics.setAddiction(getAddiction());
        basics.setChemistry(getChemistry());
        basics.setDose(getDose());
        basics.setDuration(getDuration());
        basics.setEffects(getEffects());
        basics.setHistory(getHistory());
        basics.setLaw(getLaw());
        basics.setOnset(getOnset());
        basics.setPharmacology(getPharmacology());
        basics.setPrice(getPrice());
        basics.setProduction(getProduction());
        return basics;
    }

    private String fixSummary(String text) {
        if (text.toLowerCase().contains("summary needed")) {
            return "";
        }
        return text;
    }
}
