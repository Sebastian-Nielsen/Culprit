package common.html.htmlBuilderStrategy;

import common.html.HTML;

import java.util.*;

public class HtmlBuilder implements HtmlBuilderStrategy {

	private static final String INDENT_CHAR = "  ";

	private final StringBuilder stringBuilder = new StringBuilder();
	private final Deque<HTML.Tag> openedTags  = new ArrayDeque<>();

	@Override
	public String toString() {
		return stringBuilder.toString();
	}

	@Override
	public HtmlBuilder open(HTML.Tag tag) {
		stringBuilder
				.append(getIndent())
				.append("<").append(tag).append(">\n");
		openedTags.addFirst(tag);
		return this;
	}

	@Override
	public HtmlBuilder setText(String text) {
		stringBuilder
				.append(INDENT_CHAR.repeat(openedTags.size()))
				.append(text)
				.append('\n');
		return this;
	}

	/**
	 * Precondition: the html has to be valid string format
	 */
	@Override
	public HtmlBuilder insertRaw(String html) {
		stringBuilder.append(html);
		return this;
	}

	@Override
	public HtmlBuilder openSingle(HTML.Tag tag, Map<HTML.Attribute, String> attrToVal) {
		// TODO: 16-01-2021 - this method -- heck this class -- is not that analyzable!
		stringBuilder
				.append(getIndent())
				.append("<")
				.append(tag);

		for (Map.Entry<HTML.Attribute, String> attrValEntry : attrToVal.entrySet())
			if (attrValEntry.getValue().isEmpty())
				stringBuilder
						.append(attrValEntry.getKey())
						.append(" ");
			else
				stringBuilder
						.append(" ")
						.append(attrValEntry.getKey())
						.append("=\"")
						.append(attrValEntry.getValue())
						.append('"');

		stringBuilder.append(">\n");

		return this;
	}

	@Override
	public HtmlBuilder open(HTML.Tag tag, Map<HTML.Attribute, String> attrToValue) {

		stringBuilder
				.append(getIndent())
				.append("<")
				.append(tag);

		for (Map.Entry<HTML.Attribute, String> attrValEntry : attrToValue.entrySet())
			stringBuilder
					.append(" ")
					.append(attrValEntry.getKey())
					.append("=\"")
					.append(attrValEntry.getValue())
					.append('"');

		stringBuilder.append(">\n");

		openedTags.addFirst(tag);

		return this;
	}

	@Override
	public HtmlBuilder close(HTML.Tag tag) {
		if (!openedTags.pop().equals(tag))
			throw new RuntimeException("18492");
		stringBuilder.append(getIndent()).append("</").append(tag).append(">\n");
		return this;
	}

	@Override
	public HtmlBuilder insertBuilder(HtmlBuilderStrategy builder) {
		stringBuilder.append(builder.toString());
		return this;
	}


	/* === PRIVATE METHODS */

	private String getIndent() {
		return INDENT_CHAR.repeat(openedTags.size());
	}
}
