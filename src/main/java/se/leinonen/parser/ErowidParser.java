package se.leinonen.parser;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import se.leinonen.parser.pagemodel.BasicsPage;
import se.leinonen.parser.pagemodel.DrugPage;
import se.leinonen.parser.pagemodel.EffectsPage;
import se.leinonen.parser.pagemodel.Page;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Simple parser for downloading drug information from erowid.org.
 * Uses JSoup library for HTML / DOM processing.
 *
 * @author leinonen
 */
public class ErowidParser {

    public static final String EROWID_PSYCHOACTIVES = "http://www.erowid.org/psychoactives/psychoactives.shtml";
    public static final String EROWID_CHEMICALS = "http://www.erowid.org/chemicals/chemicals.shtml";
    public static final String EROWID_BIG_CHART = "http://www.erowid.org/general/big_chart.shtml";

    private final int timeout = 3000;

    /**
     * Retrieve a Page-representation of a specific Erowid page based on the type of the parameter url.
     *
     * @param url ErowidUrl the target url.
     * @return Page-model for the specific page, or null if the document could not be downloaded.
     */
    public Page getPage(final ErowidUrl url) {
        System.out.print("GET " + url);

        Page result = null;
        if (ErowidUrl.Type.DRUG.equals(url.getType())) {

            System.out.println(" -> DrugPage");
            result = new DrugPage(url, fetchDocument(url));

        } else if (ErowidUrl.Type.BASICS.equals(url.getType())) {

            System.out.println(" -> BasicsPage");
            result = new BasicsPage(url, fetchDocument(url));

        } else if (ErowidUrl.Type.EFFECTS.equals(url.getType())) {

            System.out.println(" -> EffectsPage");
            result = new EffectsPage(url, fetchDocument(url));

        } else {
            System.out.println(" -> Page");
            result = new Page(url, fetchDocument(url));
        }

        if (null == result.getDocument()) {
            System.out.println(" -> null");
            return null;
        }
        return result.process();
    }

    /**
     * Retrieve the DOM for a specific URL so we can parse it later.
     *
     * @param url URL to be downloaded.
     * @return Document or null if there was an error.
     */
    private Document fetchDocument(final String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").timeout(timeout).get();
        } catch (HttpStatusException e) {
            // System.out.println(e.getMessage() + ": " + url);
        } catch (SocketTimeoutException e) {
            System.out.println("TIMEOUT");
        } catch (IOException e) {
            System.out.println("IO ERROR");
        }
        return doc;
    }

    private Document fetchDocument(final ErowidUrl url){
        return fetchDocument(url.getUrl());
    }

}
