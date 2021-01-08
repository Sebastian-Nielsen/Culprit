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
 * <pre>
 *      +--------------- For all files in *content*, do: --------------+
 *      | Given e.g.                                                   |
 *      |       File("C:/.../content/{relativePath}/test.md")          |
 *      | Then create                                                  |
 *      |       File("C:/.../deployment/{relativePath}/test.html")     |
 *      +--------------------------------------------------------------+
 * </pre>
 * (2) Prepare the files in *content* by e.g.
 *      (1) Adding ID-fileoption to files without one  {@link #addIdToContentFilesWithoutOne()}
 * (3) Create default index.html files in *deployment* {@link #addDefaultIndexes()}
 */
public interface Deployer {

	/**
	 *  Copy the file-hiearchy from *content* to *deployment*
	 */
	void deploy() throws IOException;

	/**
	 * Returns the deploy-file equivalent of the content-file
	 * <pre>
	 * +------------------------------------------------------------+
	 * | E.g. the delpoy-file equivalent of the content file        |
	 * |        File("C:/.../content/{relativePath}/test.md")       |
	 * | is                                                         |
	 * |        File("C:/.../deployment/{relativePath}/test.html")  |
	 * +------------------------------------------------------------+
	 * </pre>
	 */
	File getDeployEquivalentOf(File contentFile);

	/**
	 * Creates an `index.html` file for each folder in *deployment* that doesn't already have one.
	 */
	void addDefaultIndexes() throws Exception;

	/**
	 * Adds an ID fileoption (`[ID]:<> ({{uuid}})`) to files in *content* without one.
	 */
	void addIdToContentFilesWithoutOne() throws Exception;
}
