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
import static framework.FileOption.KEY;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final Validator validator;
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler, Validator validator) {
		this.fileHandler = fileHandler;
		this.validator = validator;
	}

	@Override
	public Map<KEY, String> extractKeyToValMapFrom(File file) throws IOException {
		Map<KEY, String> keysToVal = new HashMap<>();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line)) {
				FileOption fo =  new FileOption(line);
				keysToVal.put(KEY.valueOf(fo.getKey()), fo.getVal());
			} else
				break;
		}

		return keysToVal;
	}

	@Override
	public List<FileOption> extractFileOptions() throws IOException {
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