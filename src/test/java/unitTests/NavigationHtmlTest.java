package unitTests;

import common.html.NavigationHtml;
import org.junit.jupiter.api.Test;

import java.io.File;

import static testHelper.TestHelper.getResourceFile;

public class NavigationHtmlTest {

	private final String ROOT_DIRNAME = "NavigationHtmlTest_testFiles";
	private final File   ROOT_DIR     = getResourceFile(ROOT_DIRNAME);

	private final String A = "/A";
	private final String B = "/B";
	private final String C = "/nested/C";
	private final String D = "/nested/x2nested_1/D";
	private final String E = "/nested/x2nested_1/E";
	private final String F = "/nested/x2nested_2/F";
	private final String G = "/nested_v2/G";

	private final File FILE_B = getResourceFile(ROOT_DIRNAME + B + ".html");
	private final File FILE_A = getResourceFile(ROOT_DIRNAME + A + ".html");
	private final File FILE_C = getResourceFile(ROOT_DIRNAME + C + ".html");
	private final File FILE_D = getResourceFile(ROOT_DIRNAME + D + ".html");
	private final File FILE_E = getResourceFile(ROOT_DIRNAME + E + ".html");

	@Test
	public void shouldGenerateNavigationHtml() throws Exception {
		NavigationHtml navHtml = NavigationHtml.getInstance();
		navHtml.generateNavHtmlForAllFilesInDeploy();

//		String A_NavHtml = navHtml.getNavHtmlOf(FILE_A);
//		System.out.println(A_NavHtml);

		System.out.println("-----------------------");
		System.out.println("-----------------------");

		String c2_NavHtml = navHtml.getNavHtmlOf(FILE_D);
		System.out.println(c2_NavHtml);
	}

}
