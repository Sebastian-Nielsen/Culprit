package common.html.tags;

import framework.html.BodyTag;
import framework.html.Tag;

/**
 * Use this class if you don't plan to use {@link #addTag}.
 * Use this class if you plan to have the BodyTag contain a single String.
 */
public class StringBodyTag implements BodyTag, Tag {
	private String content;

	public StringBodyTag(String content) {
		this.content = content;
	}

	public String toString() {
		return "<body>\n\n" +
				content + "\n\n" +
				"</body>\n";
	}

	public void addTag(Tag tag) {
		content += '\n' + tag.toString();
	}
}



