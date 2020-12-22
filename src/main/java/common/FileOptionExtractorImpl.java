package common;

import framework.FileHandler;
import framework.FileOptionExtractor;
import framework.FileOption;
import framework.Validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;
	private Validator validator;

	public FileOptionExtractorImpl(FileHandler fileHandler, Validator validator) {
		this.fileHandler = fileHandler;
		this.validator = validator;
	}

	@Override
	public List<FileOption> extractFileOptionsFrom(File file) throws IOException {
		List<FileOption> fileOptions = new ArrayList<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();
			if (validator.isFileOption(line)) // TODO get this out in a validator class that is injectable https://stackoverflow.com/a/62629620/7123519
				fileOptions.add(new FileOption(line));
			else
				break;
		}

		return fileOptions;
	}
}

