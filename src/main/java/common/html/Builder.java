package common.html;

import java.util.*;

public class Builder {

//	public static class Builder {

//		private Tag result;
		private StringBuilder stringBuilder = new StringBuilder();

		public String getResult() {
			return stringBuilder.toString();
		}

		private Deque<HTML.Tag> openedTags = new ArrayDeque<>();

		public Builder open(HTML.Tag tag) {
			stringBuilder.append("\t".repeat(openedTags.size()) + "<" + tag + ">\n");
			openedTags.addFirst(tag);
			return this;
		}

		/**
		 * Precondition: the html has to be valid string format
		 */
		public Builder insertRaw(String html) {
			stringBuilder.append(html);
			return this;
		}

		public Builder open(HTML.Tag tag, Map<HTML.Attribute, String> attrToValue) {

			stringBuilder
					.append("\t".repeat(openedTags.size()))
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
			stringBuilder.append("\t".repeat(openedTags.size()) + "</" + tag + ">\n");
			return this;
		}

		public Builder setAttr(HTML.Attribute attr, String value) {

			return this;
		}

//	}

}
