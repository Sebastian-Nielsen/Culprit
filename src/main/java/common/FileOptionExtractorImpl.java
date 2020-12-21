package common;

import framework.FileHandler;
import framework.FileOption;
import framework.FileOptionExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static framework.FileOption.isFileOption;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	@Override
	public List<FileOption> extractFileOptionsFrom(File file) throws IOException {
		List<FileOption> FileOptions = new ArrayList<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();
			if (isFileOption(line))
				FileOptions.add(new FileOption(line));
			else
				break;
		}

		return FileOptions;
	}
}
