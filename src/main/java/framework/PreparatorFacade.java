package framework;

import common.FileHandlerImpl;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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

	Map<String, File> extractIdToDeployFile();

	/**
	 * For all content files, extracts all fileoptions in the given content-file and encapsulate them in a {@code FileOptionContainer}.
	 * @return a mapping of each file to their respective {@code FileOptionContainer}
	 */
	Map<File, FileOptionContainer> extractFOContainerFromEachContentFile() throws Exception;

	/**
	 * Extract a {@code FileOptionContainer} from the specified {@code contentFile}
	 */
	FileOptionContainer extractFoContainerFrom(File contentFile) throws IOException;
}
