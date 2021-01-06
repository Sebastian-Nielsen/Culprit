package common.html;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static common.html.Constants.CHARSET_UTF8_Attr;
import static common.html.Constants.META_CHARSET_UTF8;
import static common.html.Tag.TYPE.*;
import static common.html.Tag.TYPE.LINK;
import static framework.utils.FileUtils.Lister.*;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static common.html.HTML.Attribute.*;

public class HtmlBuilder {

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @param folder the folder that the generation of the dynamic html is based on
	 * @return {@code toString} of {@code HtmlTag}
	 */
	public static String buildDefaultIndexHtml(File folder) throws IOException {
		Tag htmlTag = new Tag(HTML);
		Tag headTag = newDefaultHeadTag();      // TODO: finsish css and html for default indexes
		Tag bodyTag = new Tag(BODY);

		Tag olTag = generateCustomOlTagForDefaultIndex(folder);
		bodyTag.addChild(olTag);

		htmlTag.addChild(headTag);
		htmlTag.addChild(bodyTag);

		return htmlTag.toString();
	}


	public static Tag buildDefaultPageHtmlFrom(String articleContent) {
		// TODO: Enhance this to depend on the fileoption specified in the file.
		Tag htmlTag = new Tag(HTML);
		Tag headTag = newDefaultHeadTag();
		Tag bodyTag = new Tag(BODY);

		Tag article    = new Tag(ARTICLE);
		Tag  leftAside = new Tag(ASIDE);
		Tag rightAside = new Tag(ASIDE);

		article.setContent(articleContent);

		bodyTag.addChild(leftAside);
		bodyTag.addChild(rightAside);
		bodyTag.addChild(article);

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
				newDefaultCssLinkTag("index.css"),
				META_CHARSET_UTF8
		));

		return headTag;
	}

	/* ============================================================ */

	/**
	 * Create a default link {@code Tag}
	 * @param hrefVal value of the HREF attribute
	 * @return
	 *      `<link rel="stylesheet" text="text/css" href="{{hrefVal}}" />`
	 */
	public static Tag newDefaultCssLinkTag(String hrefVal) {
		Tag linkTag = new Tag(LINK);

		linkTag.putAttrToVal(HREF, hrefVal);
		linkTag.putAttrToVal(REL,  "stylesheet");

		return linkTag;
	}

	/* ============================================================ */

	/* === PRIVATE METHODS */

	/**
	 * For each {@code File} (both dirs and files) in the specified folder,
	 * create a li {@code Tag} and add it to the ol {@code Tag} to return.
	 * @param folder folder from which to list the files from.
	 * @return an ol {@code Tag}
	 */
	private static Tag generateCustomOlTagForDefaultIndex(File folder) throws IOException {
		Tag olTag = new Tag(OL);

		Tag olTagForDirs  = generateCustomOlTagForDefaultIndex(listDirsFrom(   folder, NONRECURSIVE));
		Tag olTagForFiles = generateCustomOlTagForDefaultIndex(listNonDirsFrom(folder, NONRECURSIVE));

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

	private static Tag generateCustomOlTagForDefaultIndexFor(File folder) throws IOException {
		Tag olTag = new Tag(OL);

		for (File file : listNonDirsFrom(folder, NONRECURSIVE))

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

		aTag.putAttrToVal(HREF, hrefVal);
		aTag.setContent(liContent);

		liTag.addChild(aTag);

		return liTag;
	}

}
