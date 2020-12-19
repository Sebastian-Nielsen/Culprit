package stubs;

import framework.FileOption;

public class FileOptionStub implements FileOption {

	public FileOptionImpl(String FileOptionComment) {
		this.key = getFileOptionKeyOf(FileOptionComment);
		this.val = getValOf(FileOptionComment);
	}

}
