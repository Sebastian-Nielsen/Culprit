package common.html.htmlTemplatesStrategy;

import common.html.HTML;
import common.html.HtmlBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static common.html.HTML.Tag.*;
import static framework.Constants.Constants.CWD_NAME;

@SuppressWarnings("unchecked")
public class Helper {


	public static String defaultHeadTags =
			new HtmlBuilder()
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET, "utf-8"))
					.toString();



	public static @NotNull Map<HTML.Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
				HREF, "/resources/" + hrefVal,
//				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}

	public static @NotNull Map<HTML.Attribute, String> defaultScriptAttributes(String srcVal) {
		return defaultScriptAttributes(srcVal, new HashMap());
	}
	public static @NotNull Map<HTML.Attribute, String> defaultScriptAttributes(String srcVal,
	                                                                           Map<HTML.Attribute, String> attrs) {
		HashMap<HTML.Attribute, String> temp = new HashMap<>();
		temp.put(SRC, "/resources/" + srcVal);
//		temp.put(SRC, "/%s/%s".formatted(CWD_NAME, srcVal));

		return combineMaps(attrs, temp);
	}

	private static Map<HTML.Attribute, String> combineMaps(Map<HTML.Attribute, String> map1,
	                                                       Map<HTML.Attribute, String> map2) {
		Map<HTML.Attribute, String> map3 = new HashMap<>();
		map3.putAll(map1);
		map3.putAll(map2);
		return map3;
	}

}
