package common.html;

public interface NavigationHtmlGenerator {

	void generateNavHtmlForAllFiles() throws Exception;

	String getNavHtmlOf(String relFilePath);
}
