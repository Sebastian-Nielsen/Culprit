package common.fileOption;

import common.other.FileHandlerImpl;
import framework.other.FileHandler;
import framework.other.FileOptionExtractor;
import framework.other.Validator;
import common.other.ValidatorImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
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

	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder) throws Exception {
		return extractFOContainerFromEachFileIn(folder, ValidatorImpl.getInstance());
	}

	public Map<File, FileOptionContainer> extractFOContainerFromEachFileIn(File folder, Validator validator) throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = new HashMap<>();

		final String mdFilesOnly = "md";
		for (File file : listNonDirsFrom(folder, RECURSIVE, mdFilesOnly))

			fileToFOContainer.put(
					file,
					extractFOContainer(new FileHandlerImpl(file), validator)
			);

		return fileToFOContainer;
	}

}