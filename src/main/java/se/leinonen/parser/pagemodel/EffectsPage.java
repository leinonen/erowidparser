package se.leinonen.parser.pagemodel;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import se.leinonen.parser.model.DrugEffects;
import se.leinonen.parser.ErowidUrl;

public class EffectsPage extends Page {

    private Logger logger = Logger.getLogger(EffectsPage.class);

    private List<String> positiveEffects;
    private List<String> neutralEffects;
    private List<String> negativeEffects;

    private String durationChartUrl;

    private String description;

    public EffectsPage(final ErowidUrl url, final Document doc) {
        super(url, doc);
    }

    @Override
    public Page process() {

        super.process();

        setDescription(parseText("div.effects-description div.effects-text"));
        String image = parseImage("img.duration-chart");
        String imageUrl = getUrl().getBaseUrl() + "/" + getUrl().getPath() + "/" + image;
        setDurationChartUrl(imageUrl);

        String divPath = "div.effects-list div.effects-list-";
        setPositiveEffects(parseList(divPath + "positive ul.effects-item li"));
        setNegativeEffects(parseList(divPath + "negative ul.effects-item li"));
        setNeutralEffects(parseList(divPath + "neutral ul.effects-item li"));

        logger.info(getDescription());
        logger.info(getDurationChartUrl());

        logger.info("--POSITIVE EFFECTS--");
        for (String s : getPositiveEffects()) {
            logger.info(s);
        }
        logger.info("--NEGATIVE EFFECTS--");
        for (String s : getNegativeEffects()) {
            logger.info(s);
        }
        logger.info("--NEUTRAL EFFECTS--");
        for (String s : getNeutralEffects()) {
            logger.info(s);
        }
        return this;
    }

    public DrugEffects toDrugEffects() {
        DrugEffects effects = new DrugEffects();
        return effects;
    }

    public String getDescription() {
        return description;
    }

    public String getDurationChartUrl() {
        return durationChartUrl;
    }

    public List<String> getNegativeEffects() {
        return negativeEffects;
    }

    public List<String> getNeutralEffects() {
        return neutralEffects;
    }

    public List<String> getPositiveEffects() {
        return positiveEffects;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationChartUrl(String durationChartUrl) {
        this.durationChartUrl = durationChartUrl;
    }

    public void setNegativeEffects(List<String> negativeEffects) {
        this.negativeEffects = negativeEffects;
    }

    public void setNeutralEffects(List<String> neutralEffects) {
        this.neutralEffects = neutralEffects;
    }

    public void setPositiveEffects(List<String> positiveEffects) {
        this.positiveEffects = positiveEffects;
    }

}
