package common.fileOption;

import common.other.UUIDGeneratorImpl;
import framework.UUIDGenerator;

import java.io.File;
import java.io.IOException;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.insertLineAtTopOf;

public class FileOptionInserter {

	private final UUIDGenerator uuidGenerator;

	public FileOptionInserter(UUIDGenerator uuidGenerator) {
		this.uuidGenerator = uuidGenerator;
	}

	public FileOptionInserter() {
		this.uuidGenerator = UUIDGeneratorImpl.getInstance();
	}

	/**
	 * Adds a {@code FileOption} with
	 * (key: {@code FileOption.KEY.ID}) and (value: <randomlyGeneratedUUID>)
	 * to the top of the specified {@code file}.
	 */
	public void addIdTo(File file) throws IOException {
		String idFileOptionLine = generateIdFileOption();

		insertLineAtTopOf(file, idFileOptionLine);
	}


	/* === PRIVATE METHODS */

	private String generateIdFileOption() {
		String uuid = uuidGenerator.generate();
		return "[" + ID + "]:<> (" + uuid + ")";
	}
}
