package common.html;


import java.io.File;

/**
 * Telescoping constructor will do for now.
 * YAGI: no need for bloch builder yet.
 */
public class TemplateParameters {

	public final ArticleTag articleTag;
	public final File folder;

	public TemplateParameters(File folder, ArticleTag articleTag) {
		this.articleTag = articleTag;
		this.folder = folder;
	}

	public TemplateParameters(File folder) {
		this.folder = folder;
		this.articleTag = null;
	}

}
