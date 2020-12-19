package common;

import framework.FileHandler;
import org.apache.pdfbox.util.filetypedetector.FileType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandlerImpl implements FileHandler, AutoCloseable {

	private final BufferedReader reader;
	private String nextLine;

	public FileHandlerImpl(File file) throws IOException {
		reader = new BufferedReader(new FileReader(file));
		nextLine = reader.readLine();
	}

//	@Override
//	public void focus(File file) throws IOException {
//		reader.close();
//		reader = new BufferedReader(new FileReader(file));
//	}

//	public static boolean createFile(String absPath) throws IOException {
//		File file = new File(absPath);
//		System.out.println("[createFile] isFile:" + file.isFile() + ",  " + file);
//
//		if (file.isFile())
//			return file.createNewFile();
//		return file.mkdir();
//	}

	@Override
	public String readLine() throws IOException {
		String line = nextLine;
		nextLine = reader.readLine();
		return line;
	}

	@Override
	public boolean hasNext() {
		return nextLine != null;
	}

	@Override
	public void close() throws Exception {
		reader.close();
	}
}
