package common.html.tags;

import framework.html.Tag;

public class StringLiTag implements Tag {
	private final String content;

	public StringLiTag(String content) {
		this.content = content;
	}

	public String toString() {
		return "<li>" + content + "</li>\n";
	}
}
