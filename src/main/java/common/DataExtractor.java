package common;

import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.html.NavigationHtml;
import common.other.FileHandlerImpl;
import framework.other.Logger;
import one.util.streamex.EntryStream;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
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
 * <li> Extracting IDs to their relative deploy path (see {@link #extractIdToContentFile})
 *</ol>
 */
public class DataExtractor {

	private @NotNull final File contentRootFolder;
	private @NotNull final File deployRootFolder;

	public DataExtractor(@NotNull File contentRootFolder, @NotNull File deployRootFolder) {
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

	private Map<String, FileOptionContainer> extractPathToFOContainer() throws Exception {
		return EntryStream.of(extractFOContainerFromEachContentFile())
				.mapKeys(File::toString)
				.toMap();
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

		Map<String, File>                idToFile          = extractIdToContentFile();
		Map<String, FileOptionContainer> pathToFoContainer = extractPathToFOContainer();

		NavigationHtml navigationHtml = new NavigationHtml(contentRootFolder, deployRootFolder);
		navigationHtml.generateNavHtmlForAllFilesInDeploy();

		Logger.log(pathToFoContainer);

		return new CompilerDataContainer(idToFile, pathToFoContainer, navigationHtml);
	}

	/**
	 * <pre>
	 * +--------------- For all files in *content*, do: ----------+
	 * | Given an ID-value, e.g.:                                 |
	 * |       "11111111-1111-1111-1111-111111111111"             |
	 * | Then the resulting map contains the ID-value             |
	 * | pointing to the file with the given ID-value:            |
	 * |       "11111111-1111-1111-1111-111111111111"             |
	 * |            ->                                            |
	 * |        new File("C:/.../content/{relativePath}/test.md") |
	 * +----------------------------------------------------------+
	 * </pre>
	 */
	public Map<String, File> extractIdToContentFile() throws IOException {
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
