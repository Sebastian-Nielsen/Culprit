package unitTests;

import common.html.navHtmlGenerator;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static testHelper.TestHelper.getResourceFile;

public class navHtmlGeneratorTest {

	private final String ROOT_INPUT_DIRNAME = "NavigationHtmlTest_testFiles/input";
	private final File ROOT_INPUT_DIR = getResourceFile(ROOT_INPUT_DIRNAME);

	private final String A = "/A";
	private final String B = "/B";
	private final String C = "/nested/C";
	private final String D = "/nested/x2nested_1/D";
	private final String E = "/nested/x2nested_1/E";
	private final String F = "/nested/x2nested_2/x3nested_1/F";
	private final String G = "/nested/x2nested_2/x3nested_1/G";
	private final String H = "/nested/x2nested_2/H";
	private final String J = "/nested_v2/J";

	private final File FILE_B = getResourceFile(ROOT_INPUT_DIRNAME + B + ".html");
	private final File FILE_A = getResourceFile(ROOT_INPUT_DIRNAME + A + ".html");
	private final File FILE_C = getResourceFile(ROOT_INPUT_DIRNAME + C + ".html");
	private final File FILE_D = getResourceFile(ROOT_INPUT_DIRNAME + D + ".html");
	private final File FILE_E = getResourceFile(ROOT_INPUT_DIRNAME + E + ".html");
	private final File FILE_F = getResourceFile(ROOT_INPUT_DIRNAME + F + ".html");
	private final File FILE_G = getResourceFile(ROOT_INPUT_DIRNAME + G + ".html");
	private final File FILE_H = getResourceFile(ROOT_INPUT_DIRNAME + H + ".html");
	private final File FILE_J = getResourceFile(ROOT_INPUT_DIRNAME + J + ".html");

	private static final int MAX_NUM_OF_PARENTS_TO_INCLUDE = 2;

	private navHtmlGenerator navHtml;
	private String navHtmlOfFileF;

	@BeforeEach
	public void setup() throws Exception {
		// Fixture phase
		navHtml = new navHtmlGenerator(new ContentFileHierarchy(ROOT_INPUT_DIR), MAX_NUM_OF_PARENTS_TO_INCLUDE);
		// Exercise phase
		navHtml.generateNavHtmlForAllFiles();
		navHtmlOfFileF = navHtml.getNavHtmlOf(FILE_F);
//		System.out.println(navHtmlOfFileF);
	}

	@Test
	public void shouldStopIncludingNavHtmlForParentsWhenEncounteringParentMarkedAsTopicDir() {
		// This is the only test method that doesn't use the setup fixture
		// fixture
		String navHtmlOfFileJ = navHtml.getNavHtmlOf(FILE_J);
		System.out.println(navHtmlOfFileJ);

		// Verify
		int olTagOccurencesCount = countMatches(navHtmlOfFileJ, "</ol>");

		assertEquals(2, olTagOccurencesCount);

	}

	@Test
	public void shouldMarkParentFolders() {
		// Verify
		String validHrefValuesForParents = "(|..(/..)*)"; // Regex
		String classVal = "marked";

		String[] matches = getAllMatchesOfRegexInString(navHtmlOfFileF,
				"class=\".*?"+classVal+".*?\">\n *" +
					"<a href=\"" +validHrefValuesForParents+ '"'
		);

		assertEquals(2, matches.length);
	}

	@Test
	public void shouldGenerateClassAttributesWithDirAsValue() {
		// Verify
		int numberOfDirValues = countMatches(navHtmlOfFileF, "class=\"dir");

		assertEquals(4, numberOfDirValues);
	}

	@Test
	public void shouldGenerateClassAttributesWithFileAsValue() {
		// Verify phase
		int numberOfFilesValues = countMatches(navHtmlOfFileF, "class=\"file");

		assertEquals(4, numberOfFilesValues);
	}

	@Test
	public void shouldGenerateAnLiTagForEachDirAndFile() {
		// Verify phase
		int liTagCount = countMatches(navHtmlOfFileF, "</li>");

		// You have to manually count to this number in the ROOT_INPUT_DIR
		int expectedLiTagCount = 8;

		assertEquals( expectedLiTagCount, liTagCount );
	}

	@Test
	public void shouldGenerateNumberOfOlTagsInCorrespondanceToParentsToIncludeVar() {
		// Verify phase
		int olTagOccurencesCount = countMatches(navHtmlOfFileF, "</ol>");

		// An <ol> should be included for each parent to include, but you
		// also always have to include the files and dirs in the current dir
		// which explains the +1
		assertEquals(olTagOccurencesCount, MAX_NUM_OF_PARENTS_TO_INCLUDE + 1);
	}

	@Test
	public void shouldGenerateCorrectHrefValuesInNavigationHtml() {
		// Verify phase
		String[] matches = getAllMatchesOfRegexInString(navHtmlOfFileF, "href=\".*?\"");

		for (String match : matches) {
			System.out.println(match);
		}

		assertArrayEquals(
				new String[]{
					"href=\"../../x2nested_1\"",
					"href=\"..\"",
					"href=\"../../C.html\"",
					"href=\"\"",
					"href=\"../x3nested_2\"",
					"href=\"../H.html\"",
					"href=\"F.html\"",
					"href=\"G.html\""
				},
				matches
		);
	}



	/* === PRIVATE METHODS */

	private String[] getAllMatchesOfRegexInString(String input, String regex) {
		return Pattern.compile(regex)
				.matcher(input)
				.results()
				.map(MatchResult::group)
                .toArray(String[]::new);
	}
}
