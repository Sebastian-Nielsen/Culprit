package common.html;

import java.util.*;

public class Builder {

	private static final String INDENT_CHAR = "  ";

	private final StringBuilder stringBuilder = new StringBuilder();
	private final Deque<HTML.Tag> openedTags  = new ArrayDeque<>();

	@Override
	public String toString() {
		return stringBuilder.toString();
	}

	public Builder open(HTML.Tag tag) {
		stringBuilder
				.append(getIndent())
				.append("<").append(tag).append(">\n");
		openedTags.addFirst(tag);
		return this;
	}

	public Builder setText(String text) {
		stringBuilder
				.append(INDENT_CHAR.repeat(openedTags.size()))
				.append(text)
				.append('\n');
		return this;
	}

	/**
	 * Precondition: the html has to be valid string format
	 */
	public Builder insertRaw(String html) {
		stringBuilder.append(html);
		return this;
	}

	public Builder openSingle(HTML.Tag tag, Map<HTML.Attribute, String> attrToVal) {

		stringBuilder
				.append(getIndent())
				.append("<")
				.append(tag);

		for (Map.Entry<HTML.Attribute, String> attrValEntry : attrToVal.entrySet())
			stringBuilder
					.append(" ")
					.append(attrValEntry.getKey())
					.append("=\"")
					.append(attrValEntry.getValue())
					.append('"');

		stringBuilder.append(">\n");

		return this;
	}

	public Builder open(HTML.Tag tag, Map<HTML.Attribute, String> attrToValue) {

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

	public Builder close(HTML.Tag tag) {
		if (!openedTags.pop().equals(tag))
			throw new RuntimeException("18492");
		stringBuilder.append(getIndent() + "</" + tag + ">\n");
		return this;
	}

	public Builder insertBuilder(Builder builder) {
		stringBuilder.append(builder.toString());
		return this;
	}


	/* === PRIVATE METHODS */

	private String getIndent() {
		return INDENT_CHAR.repeat(openedTags.size());
	}
}
