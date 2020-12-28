package common;

import framework.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static framework.FileOption.KEY;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final Validator validator;
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler, Validator validator) {
		this.fileHandler = fileHandler;
		this.validator   = validator;
	}

	@Override
	public FileOptionContainer extractFileOptions() throws IOException {
		FileOptionContainer foContainer = new FileOptionContainer();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line))
				foContainer.put(
						FileOption.getKeyOf(line),
						FileOption.getValOf(line)
				);
			else
				break;
		}

		return foContainer;
	}

}