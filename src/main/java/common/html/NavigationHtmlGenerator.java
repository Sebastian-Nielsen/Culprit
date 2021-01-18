package common.html;

import java.io.File;

public interface NavigationHtmlGenerator {
	void generateNavHtmlForAllFilesInDeploy() throws Exception;

	String getNavHtmlOf(File deployFile);
}
