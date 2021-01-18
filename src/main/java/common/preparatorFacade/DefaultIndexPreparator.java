package common.preparatorFacade;

import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.concreteStrategy.DefaultIndexHtmlTemplate;

import java.io.File;

import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.streamNonDirsFrom;
import static framework.utils.FileUtils.Modifier.writeStringTo;
import static java.util.function.Predicate.isEqual;

/**
 * Adds default indexes (`index.html`) to *deployment* in the folders without one.
 */
public class DefaultIndexPreparator {  // TODO: this should be owned by deployer? Deployer should be responsible for deleting and creating files in deploy

	public static void addDefaultIndexesRecursivelyTo(File folder) throws Exception {

		for (File dir : listDirsFrom(folder, NONRECURSIVE))

			addDefaultIndexesRecursivelyTo(dir);

		addDefaultIndexTo(folder);
	}


	/* === PRIVATE METHODS */

	/**
	 * Add a default index `index.html` to {@code folder}
	 * if one doesn't already exist.
	 */
	private static void addDefaultIndexTo(File folder) throws Exception {

		boolean doesIndexFileExist = streamNonDirsFrom(folder, NONRECURSIVE).anyMatch(isEqual("index.html"));
		if (!doesIndexFileExist)
			createDefaultIndexIn(folder);
	}

	/**
	 * Creates an empty `index.html`
	 * @param folder folder in which to create the default-index
	 */
	private static void createDefaultIndexIn(File folder) throws Exception {

		File indexFile = new File(folder + "/index.html");

		indexFile.createNewFile();
	}


}
