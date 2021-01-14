package common.html;

public class ArticleTag {

	private String articleTagContent;

	public ArticleTag(String htmlContent) {
		articleTagContent = htmlContent;
	}

	@Override
	public String toString() {
		return articleTagContent;
	}

	public String insertInto(HtmlTemplateStrategy template) throws Exception {
		return template.buildUsing(new TemplateParameters(articleTagContent));
	}

}
