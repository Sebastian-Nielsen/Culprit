package framework;

import java.io.File;
import java.io.IOException;

/**      // TODO rewrite this
 * Facade for every class related to the preparation phase. <br><br>
 * <p>
 * The <u>preparation phase</u> includes:
 *<ul>
 * <li> Assert (and optionally fix) validity of <em>content</em> files
 * <ul><li> Example of validity assertion: `each file got all required {@code FileOption}s`
 *      <li> Example of fixing an invalid file: `adding required ID-{@code FileOption}s to files missing it`
 *              (see {@link #addIdToContentFilesWithoutOne()})
 * </ul>
 * <li> Create the <em>deploy</em> hierarchy/skeleton matching that of <em>content</em>; that is,
 *      <pre>
 *          +--------------- For all files in <em>content</em>, do: --------------+
 *          | Given e.g.                                                   |
 *          |       File("C:/.../content/{relativePath}/test.md")          |
 *          | Then create                                                  |
 *          |       File("C:/.../deployment/{relativePath}/test.html")     |
 *          +--------------------------------------------------------------+
 *      </pre>
 * <li> Optionally add/delete files from either <em>content</em> or <em>deploy</em>
 * <ul><li> Example: `create index.html files in <em>deploy</em>` (see {@link #addDefaultIndexes()})
 * </ul>
 *</ul>
 */
public interface PreparatorFacade {

	void prepare() throws Exception;

	/**
	 *  Copy the file-hiearchy from *content* to *deployment*
	 */
	void deploy() throws IOException;



	/**
	 * Creates an <em>empty</em> `index.html` file for each folder in <em></em></en>deployment</em> that doesn't already have one.
	 */
	void addDefaultIndexes() throws Exception;

	void addRequiredFileOptionsToFilesWithoutOne() throws Exception;

	/**
	 * Adds an ID fileoption (`[ID]:<> ({{uuid}})`) to files in *content* without one.
	 */
	void addIdToContentFilesWithoutOne() throws Exception;

	/**
	 * Delete recursively all folders and files except for the {@code folder} specified
	 */
	void cleanDeployDir(File folder);
}
