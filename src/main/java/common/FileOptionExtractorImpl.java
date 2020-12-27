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
	public Map<KEY, String> extractKeyToValMap() throws IOException {
		return null;
	}

	public FileOptionContainer extractFileOptions() throws IOException {
		FileOptionContainer fileOptions = new FileOptionContainer();

		while (fileHandler.hasNext()) {
			String line = fileHandler.readLine();

			if (validator.isFileOption(line))
				fileOptions.put(
						FileOption.getKeyOf(line),
						FileOption.getValOf(line)
				);
			else
				break;
		}

		return fileOptions;
	}

//	@Override
//	public Map<KEY, String> extractKeyToValMap() throws IOException {
//		Map<KEY, String> keyToVal = new HashMap<>();
//
//		while (fileHandler.hasNext()) {
//			String line = fileHandler.readLine();
//
//			if (validator.isFileOption(line)) {
//				fileOptions.put(
//						FileOption.getKeyOf(line),
//						FileOption.getValOf(line)
//				);
//			} else
//				break;
//		}
//
//		return keyToVal;
//	}

//	@Override
//	public List<FileOption> extractFileOptions() throws IOException {
//		List<FileOption> fileOptions = new ArrayList<>();
//
//		while (fileHandler.hasNext()) {
//			String line = fileHandler.readLine();
//
//			if (validator.isFileOption(line))
//				fileOptions.add(new FileOption(line));
//			else
//				break;
//		}
//
//		return fileOptions;
//	}


}