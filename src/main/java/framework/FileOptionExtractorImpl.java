package framework;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static framework.Validator.isFileOption;

public class FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	@Override
	public List<String> extractFileOptionsFrom(File file) throws IOException {
		List<String> fileOptions = new ArrayList<>();

		String line;
		while (fileHandler.hasNext()) {
			line = fileHandler.readLine();
			if (isFileOption(line))
				fileOptions.add(line);
			else
				break;
		}

		return fileOptions;
	}
}
