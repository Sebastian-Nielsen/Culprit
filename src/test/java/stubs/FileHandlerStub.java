package stubs;

import framework.FileHandler;

import java.io.File;
import java.io.IOException;


public class FileHandlerStub implements FileHandler {

	private final String[] lines;
	private int index = 0;

	public FileHandlerStub(String[] lines) {
//		System.out.println("FileHandlerStub constructor");
//		System.out.println("-----------");
//		for (String line : lines) {
//			System.out.println(line);
//		}
//		System.out.println("-----------");
		this.lines = lines;
	}

	@Override
	public String readLine() {
		try {
			return lines[index++];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public boolean hasNext() {
		return index < lines.length;
	}
}
