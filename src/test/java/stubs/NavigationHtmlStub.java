package stubs;

import common.html.NavigationHtmlGenerator;

import java.io.File;

public class NavigationHtmlStub implements NavigationHtmlGenerator {
	@Override
	public void generateNavHtmlForAllFiles() throws Exception {
	}

	@Override
	public String getNavHtmlOf(File deployFile) {
		return null;
	}
}
