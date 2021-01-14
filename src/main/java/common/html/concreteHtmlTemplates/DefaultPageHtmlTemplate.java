package common.html.concreteHtmlTemplates;

import common.html.HtmlBuilder;
import common.html.HtmlTemplateStrategy;
import common.html.TemplateParameters;

import java.util.Map;

import static common.html.HTML.Attribute.ID;
import static common.html.HTML.Tag.*;
import static common.html.concreteHtmlTemplates.Helper.defaultHead;

public class DefaultPageHtmlTemplate implements HtmlTemplateStrategy {

	@Override
	public String buildUsing(TemplateParameters parameters) {
		// TODO: Enhance this to depend on the fileoption specified in the file.
		return new HtmlBuilder()
				.open(HTML)
					.insertBuilder(defaultHead)
					.open(BODY)
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside")).close(ASIDE)
							.open(ASIDE, Map.of(ID, "right-aside")).close(ASIDE)
							.open(ARTICLE)
								.insertRaw(parameters.articleTag)
							.close(ARTICLE)
						.close(MAIN)
					.close(BODY)
				.close(HTML)
				.toString();
	}

}
