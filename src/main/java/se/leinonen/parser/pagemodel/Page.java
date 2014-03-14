package se.leinonen.parser.pagemodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import se.leinonen.parser.ErowidUrl;

public class Page {
	private Document document;
	private ErowidUrl url;
	private String title;

	private final Set<ErowidUrl> links = new HashSet<ErowidUrl>();

	public Page(final ErowidUrl url, final Document doc) {
		this.setDocument(doc);
		this.setUrl(url);
	}

	public void addLink(final ErowidUrl url) {
		if (!getLinks().contains(url))
			getLinks().add(url);
	}

	public Document getDocument() {
		return document;
	}

	public Set<ErowidUrl> getLinks() {
		return links;
	}

	public Set<ErowidUrl> getLinksOfType(ErowidUrl.Type type) {
		Set<ErowidUrl> urls = new HashSet<ErowidUrl>();
		for (ErowidUrl url : links) {
			if (url.getType().equals(type)) {
				urls.add(url);
			}
		}
		return urls;
	}

	public String getTitle() {
		return title;
	}

	public ErowidUrl getUrl() {
		return url;
	}

	public String parseImage(String query) {
		if (null == getDocument()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Element e : getDocument().select(query)) {
			sb.append(e.attr("src"));
		}
		return sb.toString();
	}

	public List<String> parseList(String query) {
		if (null == getDocument()) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();
		for (Element e : getDocument().select(query)) {
			list.add(Jsoup.parse(e.html()).text());
		}
		return list;
	}

	public String parseText(String query) {
		if (null == getDocument()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Element e : getDocument().select(query)) {
			sb.append(Jsoup.parse(e.html()).text());
		}
		return sb.toString();
	}

	public Page process() {
		processMetadata();
		processLinks();
		return this;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(ErowidUrl url) {
		this.url = url;
	}

	private void processLinks() {
		Set<String> linkset = new HashSet<String>();
		for (Element linkNode : getDocument().select("a")) {
			String link = linkNode.attr("href");
			if (!linkset.contains(link)) {
				linkset.add(link);
			}
		}
		for (String link : linkset) {
			ErowidUrl url = new ErowidUrl(link, getUrl());
			if (url.isValidErowidUrl()) {
				addLink(url);
			}
		}
	}

	private void processMetadata() {
		setTitle(getDocument().title());
	}

}
