package common.html;

import java.io.File;

public interface NavigationHtmlGenerator {

	void generateNavHtmlForAllFiles() throws Exception;

	String getNavHtmlOf(File deployFile);
}
