package common.html.tags;

import framework.html.BodyTag;
import framework.html.HeadTag;


public class HtmlTag {

	private final HeadTag headTag;
	private final BodyTag bodyTag;

	public HtmlTag(Builder builder) {
		this.headTag = builder.headTag;
		this.bodyTag = builder.bodyTag;
	}

	public String toString() {
		return //"<html>\n" +
					headTag + "" +
					bodyTag;
				//"</html>";


	}

	/* === Builder Pattern */
	public static final class Builder {
		// === Required parameters

		// === Optional parameters
		private HeadTag headTag = new DefaultHeadTag();
		private BodyTag bodyTag = new CompositeBodyTag();

		public Builder setHeadTag(HeadTag head) {
			headTag = head;
			return this;
		}
		public Builder setBodyTag(BodyTag body) {
			bodyTag = body;
			return this;
		}

		public HtmlTag build() {
			return new HtmlTag(this);
		}
	}

}
