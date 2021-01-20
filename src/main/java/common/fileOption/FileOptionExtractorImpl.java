package common.fileOption;

import common.other.FileHandlerImpl;
import framework.ContentFileHierarchy;
import framework.other.FileHandler;
import framework.other.FileOptionExtractor;
import framework.other.Validator;
import common.other.ValidatorImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private static final FileOptionExtractor instance = new FileOptionExtractorImpl();

	private FileOptionExtractorImpl() {}

	public static FileOptionExtractor getInstance() {
		return instance;
	}

	public FileOptionContainer extractFOContainer(FileHandler fileHandler) throws IOException {
		return extractFOContainer(fileHandler, ValidatorImpl.getInstance());
	}

	@Override
	public FileOptionContainer extractFOContainer(FileHandler fileHandler, Validator validator) throws IOException {
		FileOptionContainer foContainer = new FileOptionContainer();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line))
				foContainer.put(
						FileOption.parseKeyOf(line),
						FileOption.parseValOf(line)
				);
			else
				break;
		}

		return foContainer;
	}

	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(ContentFileHierarchy contentHierarchy) throws Exception {
		return extractFOContainerFromEachFileIn(contentHierarchy, ValidatorImpl.getInstance());
	}

	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(ContentFileHierarchy contentHierarchy, Validator validator) throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = new HashMap<>();

		for (File file : contentHierarchy.listNonDirs(RECURSIVELY))

			fileToFOContainer.put(
					file,
					extractFOContainer(new FileHandlerImpl(file), validator)
			);

		return fileToFOContainer;
	}

}