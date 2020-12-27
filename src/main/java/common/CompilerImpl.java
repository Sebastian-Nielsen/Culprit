package common;

import framework.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.FileOption;
import org.apache.commons.io.FileUtils;

import static framework.FileOption.KEY.*;
import static framework.FileOption.KEY;
import static framework.utils.FileUtils.*;
import static org.apache.commons.io.FileUtils.readFileToString;

public class CompilerImpl implements Compiler {
	private final File contentRootFolder;
	private final Map<String, File> IDToFile = new HashMap<>();

	public CompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	/**
	 * Go through all files while:
	 * (1) record (value of {@code ID}, {@code File}) pairs
	 * (2) Asserting that each {@code File} has all _required_ {@code FileOption}s
	 */
	public void preprocess(Map<File, Map<KEY, String>> fileToKeyToVal) {

		for (File file : fileToKeyToVal.keySet()) {

			assertHasAllRequiredFileOptions(file, fileToKeyToVal);

			recordIDAndFile(file, fileToKeyToVal);
		}

	}

	private void recordIDAndFile(File file, Map<File, Map<KEY, String>> fileToKeyToVal) {
		Map<KEY, String> keyToVal = fileToKeyToVal.get(file);

			IDToFile.put(
					fileToKeyToVal.get(file).get(ID),
					file
			);
	}

	private void assertHasAllRequiredFileOptions(File file, Map<File, Map<KEY, String>> fileToKeyToVal) {

		Set<KEY> keysOfFile = fileToKeyToVal.get(file).keySet();

		for (KEY key : FileOption.REQUIRED_KEYS) {

			boolean fileHasRequiredFileOption = keysOfFile.contains(key);
			if (!fileHasRequiredFileOption)
				throw new RuntimeException(
						"For File: " + file + "\n" +
						"For KEY: "  + key  + "\n" +
						"no value has been given and there's no default value.\n\n" +
						"Please add FileOption: `["+key+"]:<> (<value>)´\n" +
						"to File: " + file + "\n\n");
		}

	}

//	private void processFileOptionsOf(File file, List<FileOption> fileOptions) {
//		for (FileOption fileOption : fileOptions) {
//			KEY key = KEY.valueOf(fileOption.getKey());
//
//			switch (key) {
//				case ID:
//					fileToID.put(file, ID);
//					break;
//				case D_LINKS:
//					break;
//				default:
//					throw new RuntimeException("We shouldn't hit this ever");
//			}
//		}
//	}

	public Map<File, String> compile(Map<File, Map<KEY, String>> fileToKeyToVal) throws IOException {
		Map<File, String> fileToCompiledContent = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder))
			fileToCompiledContent.put(
					file,
					compile(file, fileToKeyToVal.get(file))
			);

		return fileToCompiledContent;
	}


	private String compile(File fileToCompile, Map<KEY, String> keyToVal) throws IOException {
		String content = contentOf(fileToCompile);

//		System.out.println("======");
//		System.out.println(fileToCompile);
//		System.out.println(keyToVal.get(ID));

//		String ID_val     = keyToVal.getOrDefault(KEY.ID,      KEY.ID.getDefaultVal(fileToCompile));
		String DLINKS_val = keyToVal.getOrDefault(KEY.D_LINKS, "false");

		System.out.println("(((((((((((((((((((((88");
		System.out.println(content);
		content = handleDLINKS(DLINKS_val, content, fileToCompile);

		System.out.println(fileToCompile);
		System.out.println("+" + content + "+");

		return content;
	}

	/**
	 * For each dynamic-link in {@code fileToCompileContent}, replace
	 * the ID with the relative path of the file associated with the ID,
	 * relativized in relation to {@code fileToCompile}.
	 *  E.g.    `[link text](2d424a8f-6fe8-455d-81de-6be20691cf32)`
	 *              ->
	 *          `[link text](../folder/Z.md)`
	 * @param value the value of the D_LINKS-key.
	 */
	private String handleDLINKS(String value, String contentOfFileToCompile, File fileToCompile) {
		if (value.equals("false"))
			return contentOfFileToCompile;

		String markdownLinkRegex = "\\[(.*?)]\\((.*?)\\)";
		Matcher matcher = Pattern.compile(markdownLinkRegex).matcher(contentOfFileToCompile);

		if (!matcher.find())
			return contentOfFileToCompile;

		String temp = replaceAllIDsWithFilePaths(matcher, fileToCompile);
		return temp;
	}

	private String replaceAllIDsWithFilePaths(Matcher matcher, File file) {
		StringBuffer buffer = new StringBuffer();
		do {
			String linkText   = matcher.group(1);
			String id         = matcher.group(2);
			String pathToFile = convertToRelativeFilePath(id, file);
			String replacement = "[" + linkText + "](" + pathToFile + ")";

			matcher.appendReplacement(buffer, replacement);
		} while (matcher.find());
		// For some reason stringbuffer adds '\r', which makes our tests fail because our
		// expected doesn't have any "\r", so remove all '\r'.
		return buffer.toString();
	}

	private String convertToRelativeFilePath(String id, File file) {
		Path base = Paths.get("" + file);
		Path inRelationTo = Paths.get(normalize("" + IDToFile.get(id)));
		System.out.println("okay ye");
		System.out.println("==========");
		System.out.println(id);
		for (String key : IDToFile.keySet()) {
			System.out.println(key + ">6>" + IDToFile.get(key));
		}
		System.out.println(IDToFile.get(id));
		System.out.println("==========");
		System.out.println(base);
		System.out.println(inRelationTo);
		return base.relativize(inRelationTo).toString();
	}

//	private String replaceLinksIn(File file, String toReplace) {
//		try {
//			String content = FileUtils.readFileToString(file, "UTF-8");
//			Matcher matcher = Pattern.compile(toReplace).matcher(content);
//			StringBuffer s = new StringBuffer();
//			while (matcher.find()) {
////				System.out.println("group 2 match: " + matcher.group(2) + " -> " + getPathOf(matcher.group(2)));
//				matcher.appendReplacement(s,
//						"[" + matcher.group(1) + "](" + getPathOf(matcher.group(2)) + ")");
//			}
//			content = s.toString();
//
//			FileUtils.writeStringToFile(file, content, "UTF-8");
//
//			return content;
//		} catch (IOException e) {
//			throw new RuntimeException("Generating file failed:\n " + e);
//		}
//	}

}
