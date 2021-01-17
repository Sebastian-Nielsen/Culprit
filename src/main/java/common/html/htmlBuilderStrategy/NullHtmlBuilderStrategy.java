package common.html.htmlBuilderStrategy;

import common.html.HTML;

import java.util.Map;

public class NullHtmlBuilderStrategy implements HtmlBuilderStrategy {
	@Override
	public String toString() {
		return "";
	}

	@Override
	public HtmlBuilder open(HTML.Tag tag) {
		return null;
	}

	@Override
	public HtmlBuilder open(HTML.Tag tag, Map<HTML.Attribute, String> attrToValue) {
		return null;
	}

	@Override
	public HtmlBuilder openSingle(HTML.Tag tag, Map<HTML.Attribute, String> attrToVal) {
		return null;
	}

	@Override
	public HtmlBuilder close(HTML.Tag tag) {
		return null;
	}

	@Override
	public HtmlBuilder setText(String text) {
		return null;
	}

	@Override
	public HtmlBuilder insertRaw(String html) {
		return null;
	}

	@Override
	public HtmlBuilder insertBuilder(HtmlBuilderStrategy builder) {
		return null;
	}
}
