package common;

import framework.FileHandler;
import framework.FileOptionExtractor;
import framework.FileOption;
import framework.Validator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final FileHandler fileHandler;
	private final Validator validator;

	public FileOptionExtractorImpl(FileHandler fileHandler, Validator validator) {
		this.fileHandler = fileHandler;
		this.validator = validator;
	}

	public Map<FileOption.KEY, String> extractKeyToValMapFrom(File file) throws IOException {
		Map<FileOption.KEY, String> keysToVal = new HashMap<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line)) {
				FileOption fo = new FileOption(line);
				keysToVal.put(fo.getKey(), fo.getVal());
			} else
				break;
		}

		return fileOptions;
	}

	@Override
	public List<FileOption> extractFileOptionsFrom(File file) throws IOException {
		List<FileOption> fileOptions = new ArrayList<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line))
				fileOptions.add(new FileOption(line));
			else
				break;
		}

		return fileOptions;
	}


}