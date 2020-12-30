package common.html;

import common.html.tags.*;

import java.io.File;
import java.util.List;

import static common.html.tags.Tag.TYPE.*;
import static common.html.tags.Tag.TYPE.LINK;
import static framework.utils.FileUtils.*;
import static javax.swing.text.html.HTML.Attribute.*;

public class HtmlBuilder {

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @param folder the folder that the generation of the dynamic html is based on
	 * @return {@code toString} of {@code HtmlTag}
	 */
	public static String buildDefaultIndexHtml(File folder) {
		Tag htmlTag = new Tag(HTML);
		Tag headTag = newDefaultHeadTag();
		Tag bodyTag = new Tag(BODY);

		Tag olTag = generateCustomOlTagForDefaultIndex(folder);
		bodyTag.addChild(olTag);

		htmlTag.addChild(headTag);
		htmlTag.addChild(bodyTag);

		return htmlTag.toString();
	}


	public static Tag buildHtmlTag(String htmlBody) {
		// TODO: Enhance this to depend on the fileoption specified in the file.
		Tag htmlTag = new Tag(HTML);
		Tag headTag = newDefaultHeadTag();
		Tag bodyTag = new Tag(BODY);

		bodyTag.setContent(htmlBody);

		htmlTag.addChild(headTag);
		htmlTag.addChild(bodyTag);

		return htmlTag;
	}


	/* === Default Tags */

	public static Tag newDefaultHeadTag() {
		Tag headTag = new Tag(HEAD);

		headTag.addChildren(List.of(
				newDefaultCssLinkTag("global.css"),
				newDefaultCssLinkTag("main.css"),
				newDefaultCssLinkTag("index.css")
		));

		return headTag;
	}

	/**
	 * Create a default link {@code Tag}
	 * @param hrefVal value of the HREF attribute
	 * @return
	 *      `<link rel="stylesheet" text="text/css" href="{{hrefVal}}" />`
	 */
	public static Tag newDefaultCssLinkTag(String hrefVal) {
		Tag linkTag = new Tag(LINK);

		linkTag.setAttrToVal(HREF, hrefVal);
		linkTag.setAttrToVal(REL,  "stylesheet");

		return linkTag;
	}


	/* === PRIVATE METHODS */

	/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	private static Tag generateCustomOlTagForDefaultIndex(File folder) {
		Tag olTag = new Tag(OL);

		Tag olTagForDirs  = generateCustomOlTagForDefaultIndex(  allDirsFrom(folder));
		Tag olTagForFiles = generateCustomOlTagForDefaultIndex(allNonDirFrom(folder));

		olTag.addChild(olTagForDirs);
		olTag.addChild(olTagForFiles);

		return olTag;
	}

	private static Tag generateCustomOlTagForDefaultIndex(File[] files) {
		Tag olTag = new Tag(OL);

		for (File file : files)

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

	private static Tag generateCustomOlTagForDefaultIndexFor(File folder) {
		Tag olTag = new Tag(OL);

		streamOfAllNonDirsFrom(folder).forEach(file ->

				olTag.addChild(
					// `<li>
					//      <a href="./{{file.getName()}}">
					//          {{file.getName()}}
					//      </a>
					// </li>`
					generateLiTagOf(file)
				)

		);

		return olTag;
	}

	private static Tag generateLiTagOf(File file) {
		String liContent =        file.getName();
		String hrefVal   = "./" + file.getName();

		Tag li = newClickableLi(liContent, hrefVal);

		System.out.println("Generated li: " + li);

		return li;
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

}
