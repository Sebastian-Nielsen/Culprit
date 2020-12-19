package common;

import framework.FileHandler;
import framework.FileOptionImpl;
import framework.FileOptionExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static framework.Validator.isFileOption;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	@Override
	public List<FileOptionImpl> extractFileOptionsFrom(File file) throws IOException {
		List<FileOptionImpl> fileOptionImpls = new ArrayList<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();
			if (isFileOption(line))
				fileOptionImpls.add(new FileOptionImpl(line));
			else
				break;
		}

		return fileOptionImpls;
	}
}
