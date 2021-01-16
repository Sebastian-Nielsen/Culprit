package framework;

import java.io.IOException;

/**
 * Facade for every class related to the preparation phase. <br><br>
 * <p>
 * The <u>preparation phase</u> includes:
 *<ul>
 * <li> Assert (and optionally fix) validity of *content* files
 * <ul><li> Example of validity assertion: `each file got all required {@code FileOption}s`
 *      <li> Example of fixing an invalid file: `adding required ID-{@code FileOption}s to files missing it`
 *              ({@link #addIdToContentFilesWithoutOne()})
 * </ul>
 * <li> Create the *deploy* hierarchy/skeleton matching that of *content*; that is,
 *      <pre>
 *          +--------------- For all files in *content*, do: --------------+
 *          | Given e.g.                                                   |
 *          |       File("C:/.../content/{relativePath}/test.md")          |
 *          | Then create                                                  |
 *          |       File("C:/.../deployment/{relativePath}/test.html")     |
 *          +--------------------------------------------------------------+
 *      </pre>
 * <li> Optionally add/delete files from either *content* or *deploy*
 * <ul><li> Example: `create index.html files in *deploy*` (see {@link #addDefaultIndexes()})
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
	 * Creates an `index.html` file for each folder in *deployment* that doesn't already have one.
	 */
	void addDefaultIndexes() throws Exception;

	void addRequiredFileOptionsToFilesWithoutOne() throws Exception;

	/**
	 * Adds an ID fileoption (`[ID]:<> ({{uuid}})`) to files in *content* without one.
	 */
	void addIdToContentFilesWithoutOne() throws Exception;

}
