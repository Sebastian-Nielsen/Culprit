package common.html.htmlBuilderStrategy;

import common.html.HTML;

import java.util.Map;

public interface HtmlBuilderStrategy {

	HtmlBuilder open(HTML.Tag tag);

	HtmlBuilder open(HTML.Tag tag, Map<HTML.Attribute, String> attrToValue);

	HtmlBuilder openSingle(HTML.Tag tag, Map<HTML.Attribute, String> attrToVal);


	HtmlBuilder close(HTML.Tag tag);


	HtmlBuilder setText(String text);

	HtmlBuilder insertRaw(String html);


	HtmlBuilder insertBuilder(HtmlBuilderStrategy builder);
}
