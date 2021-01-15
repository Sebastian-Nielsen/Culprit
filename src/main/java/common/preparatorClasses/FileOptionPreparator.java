package common.preparatorClasses;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;
import framework.PreparatorFacade;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.changeFileExtOfFilesIn;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;


/**
 * Responsibilities:
 * (1) add id to content files without one
 * (2) return a map of the fileoption-IDs pointing to the deploy-file of the given ID
 */
public class FileOptionPreparator {

	public final Map<String, File> idToDeployFile = new HashMap<>();
	public final FileOptionInserter fileOptionInserter;

	private final File contentRootFolder;
	private final PreparatorFacade preparator;

	/* ============================================= */

	public FileOptionPreparator(File contentRootFolder, PreparatorFacade preparator) {
		this(contentRootFolder, preparator, new FileOptionInserter());
	}

	public FileOptionPreparator(File contentRootFolder, PreparatorFacade preparator, FileOptionInserter fileOptionInserter) {
		this.preparator = preparator;
		this.fileOptionInserter = fileOptionInserter;
		this.contentRootFolder  = contentRootFolder;
	}

	/* ============================================= */


	public Map<String, File> getIdToDeployFile() throws Exception {
		Map<String, File> idToDeployFile = new HashMap<>();

		Map<File, FileOptionContainer> fileToFOContainer = preparator.extractFOContainerFromEachContentFile();

		File[] contentFiles = changeFileExtOfFilesIn(fileToFOContainer.keySet(), "html");

		for (File contentFile : contentFiles) {

			String id = fileToFOContainer
					.get(contentFile) // Gives us: foContainer of contentFile
					.get(ID);         // Gives us: id-value    of contentFile

			idToDeployFile.put(id, contentFile);
		}

		return idToDeployFile;
	}

	public void addIdToContentFilesWithoutOne() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFileIn(contentRootFolder);

		Set<File> files = fileToFOContainer.keySet();
		for (File file : files) {

			boolean foContainerOfFileHasIdKey = fileToFOContainer.get(file).containsKey(ID);
			if (!foContainerOfFileHasIdKey)

				addIdTo(file);
		}
	}


	/* === PRIVATE METHODS */

	private void addIdTo(File file) throws IOException {
		fileOptionInserter.addIdTo(file);
	}

	private Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder) throws Exception {
		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(folder);
	}

}
