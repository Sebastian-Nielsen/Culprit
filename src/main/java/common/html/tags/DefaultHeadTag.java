package common.html.tags;

import framework.html.HeadTag;
import framework.html.LinkTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultHeadTag implements HeadTag {

	private final List<LinkTag> linkTags;

	public DefaultHeadTag() {
		linkTags = new ArrayList<>();
		linkTags.add(new CssLinkTag("global.css"));
		linkTags.add(new CssLinkTag("defaultIndex.css"));
	}

	public String toString() {
		return "<head>\n" +
				getLinkTags() +
				"</head>\n";
	}

	public String getLinkTags() {
		return "\t" + linkTags.stream()
				.map(LinkTag::toString)
				.collect(Collectors.joining("\t"));
	}

	public DefaultHeadTag(List<LinkTag> linkTags) {
		this.linkTags = linkTags;
	}


}
