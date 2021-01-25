package stubs;

import common.html.NavigationHtmlGenerator;
import org.jetbrains.annotations.NotNull;

public class NavigationHtmlStub implements NavigationHtmlGenerator {

	@Override
	public void generateNavHtmlForAllFiles() throws Exception {

	}

	@Override
	public @NotNull String getNavHtmlOf(String relFilePath) {
		return null;
	}
}
