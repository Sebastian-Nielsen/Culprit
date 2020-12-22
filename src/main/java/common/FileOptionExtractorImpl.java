package common;

import framework.FileHandler;
import framework.FileOption;
import framework.FileOptionExtractor;
import framework.FileOptionImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static framework.FileOptionImpl.isFileOption;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	@Override
	public List<FileOption> extractFileOptionsFrom(File file) throws IOException {
		List<FileOption> fileOptions = new ArrayList<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();
			if (isFileOption(line)) // TODO get this out in a validator class that is injectable https://stackoverflow.com/a/62629620/7123519
				fileOptions.add(new FileOptionImpl(line));
			else
				break;
		}

		return fileOptions;
	}
}

