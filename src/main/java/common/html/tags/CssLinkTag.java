package common.html.tags;

import framework.html.LinkTag;
import framework.html.Tag;

import static java.lang.String.format;

public class CssLinkTag implements LinkTag, Tag {

	private final String src;
	private final String rel  = "stylesheet";
	private final String type = "text/css";


	public CssLinkTag(String src) {
		this.src = src;
	}

	public String toString() {
		return format("<link " +
				"src=\"%s\" "  +
				"rel=\"%s\" "  +
				"type=\"%s\">" + "\n",
				src, rel, type);
	}

//	public CssLinkTag(Builder builder) {
//		this.src  = builder.src;
//		this.rel  = builder.rel;
//		this.type = builder.type;
//	}
//	/* === Builder pattern */
//
//	public static class Builder {
//		// === Required parameters
//		private final String src;
//
//		// === Optional parameters
//		private final String rel  = "stylesheet";
//		private final String type = "text/css";
//
//		public Builder(String src) {
//			this.src = src;
//		}
//
//		public CssLinkTag build() {
//			return new CssLinkTag(this);
//		}
//	}

}
