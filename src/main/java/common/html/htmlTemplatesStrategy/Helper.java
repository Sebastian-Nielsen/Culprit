package common.html.htmlTemplatesStrategy;

import common.compilerFacade.CompilerDataContainer;
import common.html.HTML;
import common.html.HtmlBuilder;
import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.concreteStrategy.DefaultPageHtmlTemplate;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Attribute;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static framework.Constants.Constants.CWD_NAME;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;


public class Helper {


	public static String defaultHeadTags =
			new HtmlBuilder()
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET, "utf-8"))
					.openSingle(LINK, defaultCssAttributes("global.css"))
					.toString();



	public static @NotNull Map<HTML.Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
//				HREF, "/" + hrefVal,
				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}

	public static @NotNull Map<HTML.Attribute, String> defaultScriptAttributes(String srcVal) {
		return defaultScriptAttributes(srcVal, new HashMap());
	}
	public static @NotNull Map<HTML.Attribute, String> defaultScriptAttributes(String srcVal,
	                                                                           Map<HTML.Attribute, String> attrs) {
		return combineMaps(attrs,
				Map.of(
//						SRC, "/" + srcVal,
						SRC, "/%s/%s".formatted(CWD_NAME, srcVal),
						REL, "stylesheet"
				)
		);
	}

	private static Map<HTML.Attribute, String> combineMaps(Map<HTML.Attribute, String> map1,
	                                                       Map<HTML.Attribute, String> map2) {
		Map<HTML.Attribute, String> map3 = new HashMap<>();
		map3.putAll(map1);
		map3.putAll(map2);
		return map3;
	}

}
