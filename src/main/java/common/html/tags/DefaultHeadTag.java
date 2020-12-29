package common.html.tags;

import framework.html.HeadTag;

import java.util.List;

public class DefaultHeadTag implements HeadTag {

	private final List<LinkTag> linkTags;

	public DefaultBodyTag(Builder builder) {
		this.linkTags = builder.linkTags;
	}


	/* === Builder pattern */

	public static class Builder {
		// === Optional parameters


	}


}
