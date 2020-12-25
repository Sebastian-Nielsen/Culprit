package common;

import framework.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.*;

import static framework.utils.FileUtils.listAllFilesFrom;
import static framework.utils.FileUtils.listAllNonDirFilesFrom;

import framework.FileOption;

import javax.management.RuntimeErrorException;

import static framework.FileOption.KEY.*;
import static framework.FileOption.KEY;

public class CompilerImpl implements Compiler {
	private final File contentRootFolder;
	private final Map<File, FileOption.KEY> fileToID = new HashMap<>();

	public CompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	public void preprocess(Map<File, List<FileOption>> fileToListOfFileOption) {
		for (Entry<File, List<FileOption>> e : fileToListOfFileOption.entrySet()) {
			File file                    = e.getKey();
			List<FileOption> fileOptions = e.getValue();

			processFileOptionsOf(file, fileOptions);

//			fileToID.put(
//				file,
//
//			);
		}
	}

	private void processFileOptionsOf(File file, List<FileOption> fileOptions) {
		for (FileOption fileOption : fileOptions) {
			KEY key = KEY.valueOf(fileOption.getKey());

			switch (key) {
				case ID:
					fileToID.put(file, ID);
					break;
				case D_LINKS:
					break;
				default:
					throw new RuntimeException("We shouldn't hit this ever");
			}
		}
	}

	public void prescan() {
//		for (File file : listAllNonDirFilesFrom(contentRootFolder)) {
//
//		}
	}

	public Map<File, String> compile(Map<File, List<FileOption>>  fileToListOfFileOption) throws IOException {
		Map<File, String> m = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder)) {
			String compiledContent = compile(file);
			m.put(file, compiledContent);
		}

		return m;
	}

	private String compile(File file) throws IOException {
		return String.join("\n", Files.readAllLines(file.toPath()));
	}
}
