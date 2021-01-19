package common;

import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.html.NavigationHtml;
import common.html.NavigationHtmlGenerator;
import common.other.FileHandlerImpl;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import framework.other.Logger;
import one.util.streamex.EntryStream;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
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

	private @NotNull final ContentFileHierarchy contentHiearchy;
	private @NotNull final DeployFileHierarchy  deployHierarchy;

	private CompilerDataContainer   compilerDataContainer;
	private PostEffectDataContainer postEffectDataContainer;

	public DataExtractor(@NotNull ContentFileHierarchy contentHiearchy,
	                     @NotNull DeployFileHierarchy deployHierarchy) {
		this.contentHiearchy = contentHiearchy;
		this.deployHierarchy = deployHierarchy;
	}

	/**
	 * For all content files, extracts all fileoptions in the given content-file and encapsulate them in a {@code FileOptionContainer}.
	 * @return a mapping of each file to their respective {@code FileOptionContainer}
	 */
	public Map<File, FileOptionContainer> extractFOContainerFromEachContentFile() throws Exception {
		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(contentHiearchy);
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
	public CompilerDataContainer buildDataContainerForCompiler(NavigationHtmlGenerator navHtmlGenerator) throws Exception {

		Map<String, File>                idToFile          = extractIdToContentFile();
		Map<String, FileOptionContainer> pathToFoContainer = extractPathToFOContainer();

		Logger.log(pathToFoContainer);

		return new CompilerDataContainer(idToFile, pathToFoContainer, navHtmlGenerator,
				contentHiearchy.getContentRootDir(), deployHierarchy.getDeployRootDir());
	}


	public PostEffectDataContainer buildDataContainerForPostEffects(NavigationHtmlGenerator navigationHtml) {
		return new PostEffectDataContainer(
			navigationHtml
		);
	}

	/**
	 * Builds data containers. The build data containers can be retrieved by using the appropriate getter-method.
	 */
	public void buildDataContainers() throws Exception {

		int numberOfParentsToInclude = 3;
		NavigationHtml navigationHtml;
		navigationHtml = new NavigationHtml(deployHierarchy, numberOfParentsToInclude);
		navigationHtml.generateNavHtmlForAllFilesInDeploy();

		this.compilerDataContainer   = buildDataContainerForCompiler(   navigationHtml);
		this.postEffectDataContainer = buildDataContainerForPostEffects(navigationHtml);
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

		for (File contentFile : contentHiearchy.listNonDirs(RECURSIVELY)) {

			idToFile.put(
					extractFoContainerFrom(contentFile).get(ID),
					contentFile
			);
		}

		return idToFile;
	}


	/* === GETTERS */

	public @NotNull CompilerDataContainer getCompilerDataContainer() {
		return compilerDataContainer;
	}

	public @NotNull PostEffectDataContainer getPostEffectDataContainer() {
		return postEffectDataContainer;
	}

}
