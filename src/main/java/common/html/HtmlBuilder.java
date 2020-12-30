package common.html;

import common.fileOption.FileOptionContainer;
import common.html.tags.*;
import framework.html.BodyTag;
import framework.html.CompositeTag;
import framework.html.OlTag;

import java.io.File;

public class HtmlBuilder {

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @param folder the folder that the generation of the dynamic html is based on
	 * @return {@code toString} of {@code HtmlTag}
	 */
	public static String buildDefaultIndexHtml(File folder) {

		BodyTag bodyTag = new CompositeBodyTag();
		CompositeTag olTag = new CompositeOlTag();

		File[] filesInFolder = folder.listFiles();
		for (File file : filesInFolder) {

			String content = file.getName();
			bodyTag.addTag(new StringLiTag(content));

		}
		return bodyTag.toString();
	}

//	/**
//	 * Creates a {@code li} element
//	 * @param content the plaintext content of the {@code li} element
//	 */
//	public static Tag newLiTag(String content) {
//		return new StringLiTag(content);
//	}

	public static String buildHtml(String htmlBody, FileOptionContainer foContainer) {
		// TODO: Enhance this to depend on the fileoption specified in the file.
		return new HtmlTag.Builder()
				.setBodyTag(new StringBodyTag(htmlBody))
				.build()
				.toString();

	}



}
