package common;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.*;
import java.util.HashMap;
import java.util.Map;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.relativeFilePathBetween;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

/**
 * Responsible for extracting data from *content* files,
 * as well as wrapping those extracted data into a {@link CompilerDataContainer}. <br><br>
 * More specifically:
 *<ol>
 * <li> Extracting {@link FileOptionContainer}s
 * <li> Extracting IDs to their relative deploy path (see {@link #extractIdToRelativeDeployPath})
 *</ol>
 */
public class DataExtractor {

	private final File contentRootFolder;
	private final File deployRootFolder;

	public DataExtractor(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	/**
	 * For all content files, extracts all fileoptions in the given content-file and encapsulate them in a {@code FileOptionContainer}.
	 * @return a mapping of each file to their respective {@code FileOptionContainer}
	 */
	public Map<File, FileOptionContainer> extractFOContainerFromEachContentFile() throws Exception {
		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(contentRootFolder);
	}

	/**
	 * Extract a {@code FileOptionContainer} from the specified {@code contentFile}
	 */
	public static FileOptionContainer extractFoContainerFrom(File contentFile) throws IOException {
		return FileOptionExtractorImpl.getInstance()
				.extractFOContainer(new FileHandlerImpl(contentFile));
	}

	/**
	 * Extracts all necessary data for {@code CompilerDataContainer} and then builds it.
	 * @return a {@code CompilerDataContainer}
	 */
	public CompilerDataContainer buildDataContainerForCompiler() throws Exception {
		Map<String, String> idToRelativeDeployPath = extractIdToRelativeDeployPath();
		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachContentFile();

		return new CompilerDataContainer(idToRelativeDeployPath, fileToFOContainer);
	}


	/* === PRIVATE METHODS */

	/**
	 * <pre>
	 * +--------------- For all files in *content*, do: --------------+
	 * | Given e.g. the ID-value of this file:                        | WRONG fixme
	 * |       File("C:/.../content/{relativePath}/test.md")          |
	 * | Then the resulting map contains:                             |
	 * |       ID-value -> "{relativePath}/test.html"                 |
	 * +--------------------------------------------------------------+
	 * </pre>
	 */
	public Map<String, String> extractIdToRelativeDeployPath() throws Exception {

		return EntryStream.of(extractIdToFile())
				.mapValues(contentFile -> relativeFilePathBetween(contentFile, contentFile))
				.mapValues(relFilePath -> changeFileExt(relFilePath, "html"))
				.toMap();
	}

	private Map<String, File> extractIdToFile() throws IOException {
		Map<String, File> idToFile = new HashMap<>();

		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {

			idToFile.put(
					extractFoContainerFrom(contentFile).get(ID),
					contentFile
			);
		}

		return idToFile;
	}


}
