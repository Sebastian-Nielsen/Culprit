package common;

import framework.*;
import framework.singleClasses.FileOption;
import framework.singleClasses.FileOptionContainer;
import framework.singleClasses.ValidatorImpl;

import java.io.IOException;

public class  FileOptionExtractorImpl implements FileOptionExtractor {
	private final Validator validator;
	private final FileHandler fileHandler;

	public FileOptionExtractorImpl(FileHandler fileHandler, Validator validator) {
		this.fileHandler = fileHandler;
		this.validator   = validator;
	}

	public FileOptionExtractorImpl(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
		this.validator = ValidatorImpl.getInstance();
	}

	@Override
	public FileOptionContainer extractFOContainer() throws IOException {
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