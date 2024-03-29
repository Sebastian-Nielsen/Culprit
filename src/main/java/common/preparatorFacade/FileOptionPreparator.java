package common.preparatorFacade;

import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.fileOption.FileOptionInserter;
import framework.ContentFileHierarchy;
import framework.PreparatorFacade;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;


/**
 * Responsibilities:
 * (1) add an ID-{@link FileOption} to content files without one
 * (2) return a map of the {@link FileOption}-IDs pointing to the deploy-file of the given ID
 */
public class FileOptionPreparator {

	public final Map<String, File> idToDeployFile = new HashMap<>();
	public final FileOptionInserter fileOptionInserter;

	private final ContentFileHierarchy contentHierarchy;
	private final PreparatorFacade preparator;

	/* ============================================= */

	public FileOptionPreparator(ContentFileHierarchy contentHierarchy, PreparatorFacade preparator) {
		this(contentHierarchy, preparator, new FileOptionInserter());
	}

	public FileOptionPreparator(ContentFileHierarchy contentHierarchy, PreparatorFacade preparator, FileOptionInserter fileOptionInserter) {
		this.preparator = preparator;
		this.fileOptionInserter = fileOptionInserter;
		this.contentHierarchy = contentHierarchy;
	}

	/* ============================================= */


	public void addIdToContentFilesWithoutOne() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFileIn(contentHierarchy);

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

	private Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(ContentFileHierarchy contentFileHierachy) throws Exception {
		return FileOptionExtractorImpl.getInstance()
				.extractContentToFOContainer(contentFileHierachy);
	}

}
