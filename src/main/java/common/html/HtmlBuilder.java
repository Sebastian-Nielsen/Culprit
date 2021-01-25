package common.html;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HtmlBuilder {

	private static final String INDENT_CHAR = " ".repeat(2);

	private final StringBuilder stringBuilder;
	private final Deque<HTML.Tag> openedTags;

	public String toString() {
		return stringBuilder.toString();
	}

	public HtmlBuilder() {
		stringBuilder = new StringBuilder();
		openedTags = new ArrayDeque<>();
	}

	private HtmlBuilder(StringBuilder stringBuilder, Deque<HTML.Tag> openedTags) {
		this.stringBuilder = stringBuilder;
		this.openedTags = openedTags;
	}

	/**
	 * Clones this instance; that is, create a new HtmlBuilder
	 * @return a clone of this instance
	 */
	public HtmlBuilder clone() {
		return new HtmlBuilder(
				new StringBuilder(stringBuilder),
				new ArrayDeque<>(openedTags)
		);
	}

	public HtmlBuilder open(HTML.Tag tag) {
		stringBuilder
				.append(getIndent())
				.append("<").append(tag).append(">\n");
		openedTags.addFirst(tag);
		return this;
	}

	public HtmlBuilder setText(String text) {
		stringBuilder
				.append(INDENT_CHAR.repeat(openedTags.size()))
				.append(text)
				.append('\n');
		return this;
	}

	/**
	 * Insert html in the form of a raw String.
	 * Precondition: the html is valid (e.g. all tags that are opened are also closed)
	 */
	public HtmlBuilder insert(@NotNull String html) {
		stringBuilder.append(html);
		return this;
	}

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

	public HtmlBuilder close(HTML.Tag tag) {
		if (!openedTags.pop().equals(tag))
			throw new RuntimeException("18492");
		stringBuilder.append(getIndent()).append("</").append(tag).append(">\n");
		return this;
	}

//	public HtmlBuilder insertBuilder(HtmlBuilderStrategy builder) {
//		stringBuilder.append(builder.toString());
//		return this;
//	}


	/* === PRIVATE METHODS */

	private String getIndent() {
		return INDENT_CHAR.repeat(openedTags.size());
	}
}
