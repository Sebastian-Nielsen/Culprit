package common.html.preparatorClasses;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static common.fileOption.FileOption.KEY.ID;

public class FileOptionPreparator {

	final FileOptionInserter fileOptionInserter;
	private final File contentRootFolder;

	/* ============================================= */

	public FileOptionPreparator(File contentRootFolder) {
		fileOptionInserter = new FileOptionInserter();
		this.contentRootFolder = contentRootFolder;
	}
	public FileOptionPreparator(File contentRootFolder, FileOptionInserter fileOptionInserter) {
		this.fileOptionInserter = fileOptionInserter;
		this.contentRootFolder  = contentRootFolder;
	}

	/* ============================================= */

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
