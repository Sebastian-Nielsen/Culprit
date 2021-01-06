package common.html;

import java.util.List;
import java.util.Map;

import static common.html.HTML.Attribute.CHARSET;
import static common.html.HTML.Attribute.*;
import static common.html.Tag.TYPE.LINK;
import static common.html.Tag.TYPE.META;

public class Constants {

	public static final Map<HTML.Attribute, String> CHARSET_UTF8_Attr = Map.of(CHARSET, "utf-8"); // No "charset" in HTML.Attribute, so we can't make charset a constant

	public static final Tag META_CHARSET_UTF8 = new Tag(META, CHARSET_UTF8_Attr);


	public static final Map<HTML.Attribute, String> DEFAULT_CSS_ATTRIBUTES =
			Map.of(
					REL,  "stylesheet",
					TYPE, "text/css"
			);

	public static final Tag globalCssLink =
			new Tag(LINK, DEFAULT_CSS_ATTRIBUTES);

	public static final List<Tag> DEFAULT_HEADER_TAGS = List.of(

	);


}
