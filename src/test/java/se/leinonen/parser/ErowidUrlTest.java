package se.leinonen.parser;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class ErowidUrlTest {

	private static final String PLANTS_CATEGORY = "plants";
	private static final String CHEMICALS_CATEGORY = "chemicals";
	private static final String HTTP_WWW_EROWID_ORG = "http://www.erowid.org";
	private static final String DRUG_URL = "http://www.erowid.org/plants/mushrooms/mushrooms.shtml";
	private static final String CHEM_URL = "http://www.erowid.org/chemicals/dmt/dmt.shtml";
	private static final String BASICS_URL = "mushrooms_basics.shtml";
	private static final String EFFECTS_URL = "mushrooms_effects.shtml";

	@Test
	public void abs() {
		ErowidUrl sut = new ErowidUrl("http://www.erowid.org/chemicals/opiates/opiates.shtml");
		assertEquals(sut.getOriginalUrl(), "http://www.erowid.org/chemicals/opiates/opiates.shtml");
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), "chemicals");
		assertEquals(sut.getPath(), "chemicals/opiates");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getUrl(), "http://www.erowid.org/chemicals/opiates/opiates.shtml");
		assertEquals(sut.getSimpleName(), "opiates");
		assertTrue(sut.isValid());
	}

	@Test
	public void basicsUrl() {
		ErowidUrl sut = new ErowidUrl(BASICS_URL);
		assertEquals(sut.getOriginalUrl(), BASICS_URL);
		assertEquals(sut.getBaseUrl(), "");
		assertEquals(sut.getCategory(), "");
		assertEquals(sut.getPath(), "");
		assertEquals(sut.getFileName(), BASICS_URL);
		assertEquals(sut.getType(), ErowidUrl.Type.BASICS);
		assertEquals(sut.getUrl(), "mushrooms_basics.shtml");
		assertEquals(sut.getSimpleName(), "");
		assertFalse(sut.isValid());
	}

	@Test
	public void basicsUrl1() {
		ErowidUrl parent = new ErowidUrl("http://www.erowid.org/psychoactives/psychoactives.shtml");
		ErowidUrl sut = new ErowidUrl("http://www.erowid.org/psychoactives/plants/mushrooms/mushrooms.shtml", parent);
		assertEquals(sut.getOriginalUrl(), "http://www.erowid.org/psychoactives/plants/mushrooms/mushrooms.shtml");
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), "plants");
		assertEquals(sut.getPath(), "plants/mushrooms");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getUrl(), "http://www.erowid.org/plants/mushrooms/mushrooms.shtml");
		assertEquals(sut.getSimpleName(), "mushrooms");
		assertTrue(sut.isValid());
	}

	@Test
	public void basicsUrlWithParent() {
		ErowidUrl parent = new ErowidUrl(DRUG_URL);
		ErowidUrl sut = new ErowidUrl(BASICS_URL, parent);
		assertEquals(sut.getOriginalUrl(), BASICS_URL);
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), "plants");
		assertEquals(sut.getPath(), "plants/mushrooms");
		assertEquals(sut.getFileName(), BASICS_URL);
		assertEquals(sut.getType(), ErowidUrl.Type.BASICS);
		assertEquals(sut.getUrl(), "http://www.erowid.org/plants/mushrooms/mushrooms_basics.shtml");
		assertEquals(sut.getSimpleName(), "mushroomsbasics");
		assertTrue(sut.isValid());
	}

	@Test
	public void chemUrlWorks() {
		ErowidUrl sut = new ErowidUrl(CHEM_URL);
		assertEquals(sut.getOriginalUrl(), CHEM_URL);
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), CHEMICALS_CATEGORY);
		assertEquals(sut.getPath(), "chemicals/dmt");
		assertEquals(sut.getFileName(), "dmt.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getUrl(), CHEM_URL);
		assertEquals(sut.getSimpleName(), "dmt");
		assertTrue(sut.isValid());
	}

	@Test
	public void completeUrlWorks() {
		ErowidUrl sut = new ErowidUrl(DRUG_URL);
		assertEquals(sut.getOriginalUrl(), DRUG_URL);
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), PLANTS_CATEGORY);
		assertEquals(sut.getPath(), "plants/mushrooms");
		assertEquals(sut.getFileName(), "mushrooms.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getSimpleName(), "mushrooms");
		assertEquals(sut.getUrl(), DRUG_URL);
		assertTrue(sut.isValid());
	}

	@Test
	public void effectsUrl() {
		ErowidUrl sut = new ErowidUrl(EFFECTS_URL);
		assertEquals(sut.getOriginalUrl(), EFFECTS_URL);
		assertEquals(sut.getBaseUrl(), "");
		assertEquals(sut.getCategory(), "");
		assertEquals(sut.getPath(), "");
		assertEquals(sut.getFileName(), EFFECTS_URL);
		assertEquals(sut.getType(), ErowidUrl.Type.EFFECTS);
		assertEquals(sut.getUrl(), EFFECTS_URL);
		assertFalse(sut.isValid());
	}

	@Test(enabled = false)
	public void incompleteWithParent() {
		ErowidUrl parent = new ErowidUrl(DRUG_URL);
		ErowidUrl sut = new ErowidUrl(BASICS_URL, parent);
		assertEquals(sut.getOriginalUrl(), BASICS_URL);
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), PLANTS_CATEGORY);
		assertEquals(sut.getPath(), "plants/mushrooms");
		assertEquals(sut.getFileName(), "mushrooms_basics.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.BASICS);
		assertEquals(sut.getUrl(), "http://www.erowid.org/plants/mushrooms/mushrooms_basics.shtml");
		assertTrue(sut.isValid());
	}

	@Test(enabled = false)
	public void malformedErowidUrl() {
		ErowidUrl sut = new ErowidUrl("http://www.erowid.org/psychoactives/chemicals/2ci_nbome/2ci_nbome.shtml");
		assertEquals(sut.getOriginalUrl(), "http://www.erowid.org/psychoactives/chemicals/2ci_nbome/2ci_nbome.shtml");
		assertEquals(sut.getBaseUrl(), HTTP_WWW_EROWID_ORG);
		assertEquals(sut.getCategory(), "chemicals");
		assertEquals(sut.getPath(), "chemicals/2ci_nbome");
		assertEquals(sut.getFileName(), "2ci_nbome.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getUrl(), "http://www.erowid.org/chemicals/2ci_nbome/2ci_nbome.shtml");
		assertEquals(sut.getSimpleName(), "2cinbome");
		assertTrue(sut.isValid());
	}

	@Test
	public void nitrous() {
		ErowidUrl sut = new ErowidUrl("http://www.erowid.org/psychoactives/chemicals/nitrous/nitrous.shtml");
		assertEquals(sut.getOriginalUrl(), "http://www.erowid.org/psychoactives/chemicals/nitrous/nitrous.shtml");
		assertEquals(sut.getUrl(), "http://www.erowid.org/chemicals/nitrous/nitrous.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.DRUG);
		assertEquals(sut.getSimpleName(), "nitrous");
		assertTrue(sut.isValid());
	}

	@Test
	public void psychoactives() {
		ErowidUrl sut = new ErowidUrl("http://www.erowid.org/psychoactives/psychoactives.shtml");
		assertEquals(sut.getOriginalUrl(), "http://www.erowid.org/psychoactives/psychoactives.shtml");
		assertEquals(sut.getBaseUrl(), "http://www.erowid.org");
		assertEquals(sut.getCategory(), "");
		assertEquals(sut.getPath(), "psychoactives");
		assertEquals(sut.getFileName(), "psychoactives.shtml");
		assertEquals(sut.getType(), ErowidUrl.Type.UNKNOWN);
		assertEquals(sut.getUrl(), "http://www.erowid.org/psychoactives/psychoactives.shtml");
		assertTrue(sut.isValid());
	}

	@Test
	public void shroomeryUrlWorks() {
		ErowidUrl sut = new ErowidUrl("http://www.shroomery.org");
		assertEquals(sut.getOriginalUrl(), "http://www.shroomery.org");
		assertEquals(sut.getBaseUrl(), "");
		assertEquals(sut.getCategory(), "");
		assertEquals(sut.getPath(), "");
		assertEquals(sut.getFileName(), "http://www.shroomery.org");
		assertEquals(sut.getType(), ErowidUrl.Type.UNKNOWN);
		assertEquals(sut.getUrl(), "http://www.shroomery.org");
		assertFalse(sut.isValid());
	}
}
