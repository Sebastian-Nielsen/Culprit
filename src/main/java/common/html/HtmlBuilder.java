package common.html;

import common.fileOption.FileOptionContainer;
import common.html.tags.DefaultBodyTag;
import common.html.tags.HtmlTag;
import framework.html.BodyTag;

public class HtmlBuilder {

	public static HtmlTag buildHtmlTag(String htmlBody, FileOptionContainer foContainer) {
		return new HtmlTag.Builder()
				.setBodyTag(new DefaultBodyTag(htmlBody))
				.build();

	}

}
