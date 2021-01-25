package common.html;

import org.jetbrains.annotations.NotNull;

public interface NavigationHtmlGenerator {

	void generateNavHtmlForAllFiles() throws Exception;

	@NotNull String getNavHtmlOf(String relFilePath);
}
