package unitTests;

import common.html.NavigationHtml;
import org.junit.jupiter.api.Test;

import java.io.File;

import static testHelper.TestHelper.getResourceFile;

public class NavigationHtmlTest {

	private final String ROOT_DIRNAME = "basicFileHierarchy/root";
	private final File   ROOT_DIR     = getResourceFile(ROOT_DIRNAME);

	private final File FILE_A  = getResourceFile(ROOT_DIRNAME + "/A");
	private final File FILE_B  = getResourceFile(ROOT_DIRNAME + "/lvlB/B");
	private final File FILE_C1 = getResourceFile(ROOT_DIRNAME + "/lvlB/lvlC1/C1");
	private final File FILE_C2 = getResourceFile(ROOT_DIRNAME + "/lvlB/lvlC2/C2");

	@Test
	public void shouldGenerateNavigationHtml() throws Exception {
		NavigationHtml navHtml = NavigationHtml.getInstance();
		navHtml.generateNavHtmlFor(ROOT_DIR);

		String c2NavHtml = navHtml.getNavHtmlOf(FILE_C2);
		System.out.println(c2NavHtml);

	}

}
