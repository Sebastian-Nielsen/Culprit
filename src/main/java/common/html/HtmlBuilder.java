package common.html;

import common.fileOption.FileOptionContainer;
import common.html.tags.*;

import javax.swing.text.html.HTML;
import java.io.File;

import static common.html.Constants.DEFAULT_HEADER_TAGS;
import static common.html.tags.Tag.TYPE.*;
import static javax.swing.text.html.HTML.Attribute.HREF;

public class HtmlBuilder {

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @param folder the folder that the generation of the dynamic html is based on
	 * @return {@code toString} of {@code HtmlTag}
	 */
	public static String buildDefaultIndexHtml(File folder) {
		Tag bodyTag = new Tag(BODY);

		Tag olTag = generateOlTagOfAllFilesIn(folder);
		bodyTag.addChild(olTag);

		return bodyTag.toString();
	}

	private static Tag generateOlTagOfAllFilesIn(File folder) {
		Tag olTag = new Tag(OL);

		File[] filesInFolder = folder.listFiles();
		for (File file : filesInFolder)

			olTag.addChild(
					// `<li>
					//      <a href="./{{file.getName()}}">
					//          {{file.getName()}}
					//      </a>
					// </li>`
					generateLiTagOf(file)
			);

		return olTag;
	}

	private static Tag generateLiTagOf(File file) {
		String liContent =        file.getName();
		String hrefVal   = "./" + file.getName();

		return newClickableLi(liContent, hrefVal);
	}

	/**
	 *
	 * @param liContent the "paintext"-content between the {@code li} tags, e.g.
	 *                  `<li> plaintext content </li>`
	 * @param hrefVal the value of the href to be triggered when the {@code li} is clicked.
	 * @return Generate a clickable li of the format:
	 *              `<li> <a href="{{hrefVal}}"> {{content}} </a> </li>`
	 */
	private static Tag newClickableLi(String liContent, String hrefVal) {
		Tag liTag = new Tag(LI);
		Tag  aTag = new Tag(A);

		aTag.setAttrToVal(HREF, hrefVal);
		aTag.setContent(liContent);

		liTag.addChild(aTag);

		return liTag;
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
		Tag htmlTag = new Tag(HTML);
		Tag headTag = new Tag(HEAD);
		Tag bodyTag = new Tag(BODY);

		headTag.addChildren(DEFAULT_HEADER_TAGS);
		bodyTag.setContent(htmlBody);

		htmlTag.addChild(headTag);
		htmlTag.addChild(bodyTag);

		return htmlTag.toString();
	}



}
