package framework;

import java.io.File;
import java.io.IOException;

/**
 * Abstract:
 * Prepares *content* files and creates the *deploy* hierarchy/skeleton with
 * empty .html files based on the *content* files-hierarchy.
 *
 * Responsibilities:
 * (1) Copy the file-hiearachy from *content* to *deployment*; that is:
 *      +--------------- For all files in *content*, do: --------------+
 *      | Given e.g.                                                   |
 *      |       File("C:/.../content/{relativePath}/test.md")          |
 *      | Then create                                                  |
 *      |       File("C:/.../deployment/{relativePath}/test.html")     |
 *      +--------------------------------------------------------------+
 * (2) Prepare the files in *content* by e.g.
 *      (1) Adding ID-fileoption to files without one
 */
public interface Deployer {

	/**
	 *  Copy the file-hiearchy from *content* to *deployment*
	 */
	void deploy() throws IOException;

	/**
	 * Returns the deploy-file equivalent of the content-file
	 */
	File getDeployEquivalentOf(File contentFile);

	/**
	 * Creates an `index.html` file for each folder in *deployment* that doesn't already have one.
	 */
	void addDefaultIndexes() throws IOException;

	void addIdToContentFilesWithoutOne() throws Exception;
}
