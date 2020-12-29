package common.html.tags;

import framework.html.BodyTag;
import framework.html.Tag;

import java.util.ArrayList;
import java.util.List;

public class DefaultBodyTag implements Tag, BodyTag {

	private final String content;

	public DefaultBodyTag(String content) {
		this.content = content;
	}

	public DefaultBodyTag() {
		this.content = "";
	}

}
