package common;

import framework.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static framework.utils.FileUtils.listAllNonDirFilesFrom;

import framework.FileOption;

import static framework.FileOption.KEY.*;
import static framework.FileOption.KEY;

public class CompilerImpl implements Compiler {
	private final File contentRootFolder;
	private final Map<File, FileOption.KEY> fileToID = new HashMap<>();

	public CompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	public void preprocess(Map<File, Map<String, String>> fileToKeyToVal) {
		for (File file : fileToKeyToVal.keySet()) {
			Map<String, String> keyToVal = fileToKeyToVal.get(file);

			System.out.println("12038: " + keyToVal.get(ID));

//			processFileOptionsOf(file, fileOptions);

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


	public Map<File, String> compile(Map<File, Map<KEY, String>> fileToKeyToVal) throws IOException {
		Map<File, String> fileToCompiledContent = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder))
			fileToCompiledContent.put(
					file,
					compile(file, fileToKeyToVal.get(file))
			);


		return fileToCompiledContent;
	}

	private String compile(File file, Map<KEY, String> keyToVal) throws IOException {
		String ID_val     = keyToVal.get(KEY.ID);
		String DLINKS_val = keyToVal.getOrDefault(KEY.D_LINKS, "false");


		return String.join("\n", Files.readAllLines(file.toPath()));
	}
}
