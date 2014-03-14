package se.leinonen.app;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import org.apache.log4j.Logger;
import se.leinonen.parser.ErowidParser;
import se.leinonen.parser.ErowidUrl;
import se.leinonen.parser.model.Drug;
import se.leinonen.parser.model.DrugBasics;
import se.leinonen.parser.pagemodel.BasicsPage;
import se.leinonen.parser.pagemodel.DrugPage;
import se.leinonen.parser.pagemodel.EffectsPage;
import se.leinonen.parser.pagemodel.Page;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private Logger logger = Logger.getLogger(Main.class);
	public static void main(String[] args) {
		Main app = new Main();
		//app.downloadDrugs();
		app.test();
	}

	private final ErowidParser parser = new ErowidParser();

	public void downloadDrugs() {
		ErowidUrl erowidUrl = new ErowidUrl(ErowidParser.EROWID_BIG_CHART);
		Page rootPage = parser.getPage(erowidUrl);

		for (ErowidUrl drugPageUrl : getDrugPageUrls(erowidUrl, rootPage)) {
			if (notBlacklisted(drugPageUrl)) {
				DrugPage drugPage = (DrugPage) parser.getPage(drugPageUrl);
				Drug drug = processDrugPage(drugPage);

				for (ErowidUrl url : drugPage.getLinksOfType(ErowidUrl.Type.BASICS)) {
					logger.info("-> Downloading Basic info for " + drug.getName() + ".");
					processBasics((BasicsPage) parser.getPage(url), drug);
				}

				for (ErowidUrl url : drugPage.getLinksOfType(ErowidUrl.Type.EFFECTS)) {
					logger.info("-> Downloading Effects info for " + drug.getName() + ".");
					processEffects((EffectsPage) parser.getPage(url), drug);
				}
			}
		}
	}

	public void test() {
		Page p = parser.getPage(new ErowidUrl("http://www.erowid.org/chemicals/2cb/2cb_effects.shtml"));
	}

	private List<ErowidUrl> getDrugPageUrls(ErowidUrl erowidUrl, Page rootPage) {
		List<ErowidUrl> goodUrls = new ArrayList<ErowidUrl>();
		for (ErowidUrl currentUrl : rootPage.getLinks()) {
			ErowidUrl yes = new ErowidUrl(currentUrl.getOriginalUrl(), erowidUrl);
			if (yes.getType() == ErowidUrl.Type.DRUG) {
				// TODO: Varför måste jag skapa en ny url? igen.. Annars blir simplename fel...
				goodUrls.add(new ErowidUrl(yes.getUrl()));
			}
		}
		return goodUrls;
	}

	private Query<Drug> getDrugQuery() {
		return Ebean.find(Drug.class).orderBy("name");
	}

	private boolean notBlacklisted(ErowidUrl url) {
		String urlString = url.getUrl();
		String[] blacklist = { "images", "dose", "cultivation1", "writings", "law",
                                "journal", "media", "faq", "faqs", "testing", "health",
                                "chemistry", "synthesis", "synthesis1", "spirit" };
		for (String entry : blacklist) {
			if (urlString.endsWith(entry + ".shtml")) {
				return false;
			}
		}
		if (!urlString.endsWith(".shtml") && "".equals(url.getSimpleName())) {
			return false;
		}
		return true;
	}

	private void processBasics(BasicsPage page, Drug drug) {
		DrugBasics drugBasics = page.toDrugBasics();
		if (drugBasics != null && drug != null) {
			drug.setBasics(drugBasics);
			Ebean.update(drug);
			logger.info("-> Updated " + drug.getName() + " with Basic info.");
		}
	}

	private Drug processDrugPage(DrugPage page) {
		Drug drug = page.toDrug();
		if (drug != null) {
			Ebean.save(drug);
			logger.info("-> Saved " + drug.getName() + " to database.");
		}
		return drug;
	}

	private void processEffects(EffectsPage page, Drug drug) {
		//logger.info("-> Updated " + drug.getName() + " with Effects info.");
		logger.info("-> TODO: Implement Update " + drug.getName() + " with Effects info.");
		// DrugEffects drugEffects = page.toDrugEffects();
		// drug.setEffects(drugEffects);
	}
}
