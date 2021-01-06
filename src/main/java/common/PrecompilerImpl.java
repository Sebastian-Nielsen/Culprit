package common;

import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;
import framework.Precompiler;
import framework.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.fileOption.FileOption.KEY;
import static common.fileOption.FileOption.KEY.ID;
import static framework.singleClasses.ValidatorImpl.REGEXES;
import static framework.utils.FileUtils.Filename.changeFileExt;
import static framework.utils.FileUtils.Filename.normalize;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsRecursivelyFrom;
import static framework.utils.FileUtils.Retriever.contentOf;

public class PrecompilerImpl implements Precompiler {
	private final File contentRootFolder;
	private final Map<String, File> idToDeployFile = new HashMap<>();

	public PrecompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	public Map<File, String> compileAllFiles(Map<File, FileOptionContainer> fileToFOContainer) throws IOException {
		preprocess(fileToFOContainer);

		Map<File, String> fileToCompiledContent = new HashMap<>();

		for (File file : listNonDirsFrom(contentRootFolder, RECURSIVE))

			fileToCompiledContent.put(
					file,
					compileSingleFile(file, fileToFOContainer.get(file))
			);

		return fileToCompiledContent;
	}

	public String compileSingleFile(File fileToCompile, @NotNull FileOptionContainer foContainer) throws IOException {
		String     ID_val = foContainer.get(KEY.ID); // Required FileOption
		String DLINKS_val = foContainer.getOrDefault(KEY.D_LINKS, "false");

		String content = contentOf(fileToCompile);

		content = handleDLINKS(DLINKS_val, content, fileToCompile);

		return content;
	}


	/* === PRIVATE METHODS */

	/**
	 * Go through all files while:
	 * (1) Asserting each {@code File} has all _required_ {@code FileOption}s
	 * (2) Store ({@code ID}, deploy-{@code File}) pair in map
	 */
	private void preprocess(Map<File, FileOptionContainer> fileToFOContainer) {

		Set<File> files = fileToFOContainer.keySet();
		for (File file : files) {

			if (!file.getName().endsWith(".md")) continue;

			assertFileHasAllRequiredFileOptions(file, fileToFOContainer);

			putIDToDeployFile(file, fileToFOContainer);

		}

	}

	private void putIDToDeployFile(File file, Map<File, FileOptionContainer> fileToFOContainer) {

		File htmlFile = new File(changeFileExt("" + file, "html"));

		FileOptionContainer foContainer = fileToFOContainer.get(file);
		String id         = foContainer.get(ID);

		idToDeployFile.put(id, htmlFile);
	}

	private void assertFileHasAllRequiredFileOptions(File file, Map<File, FileOptionContainer> fileToFOContainer) {

		Set<KEY> keysOfFile = fileToFOContainer.get(file).keySet();

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

		String markdownLinkRegex = "\\[(.*?)]\\((%s)\\)".formatted(REGEXES.UUID);
		Matcher matcher = Pattern.compile(markdownLinkRegex).matcher(contentOfFileToCompile);

		if (!matcher.find())
			return contentOfFileToCompile;

		return replaceAllIDsWithFilePaths(matcher, fileToCompile);
	}

	private String replaceAllIDsWithFilePaths(Matcher matcher, File file) {
		StringBuffer buffer = new StringBuffer();
		do {
			String linkText = matcher.group(1);
			String id       = matcher.group(2);
			File fileOfID     = idToDeployFile.get(id);
			String pathToFile = relativeFilePathBetween(file, fileOfID);
			String replacement = "[" + linkText + "](" + pathToFile + ")";

			matcher.appendReplacement(buffer, replacement);
		} while (matcher.find());

		matcher.appendTail(buffer);

		return buffer.toString();
	}

	/**
	 * Calculates the relative file path; E.g.
	 * +-------------------------------------------------------------------------+
	 * |baseFile: "resources/compilerTest_testFiles/D_LINKS/expected/nested/C.md"|
	 * |  toFile: "resources/compilerTest_testFiles/D_LINKS/expected/A.md"       |
	 * |                ->                                                       |                    |
	 * | @return    "./../A.md"                                                  |
	 * +-------------------------------------------------------------------------+
	 * @param baseFile file from which to start  the relative path from
	 * @param toFile   file to   which to end up the relative path to
	 * @return relative filePath, e.g. "./../B.md"
	 */
	private String relativeFilePathBetween(File baseFile, File toFile) {
		Path baseFilePath = Paths.get("" + baseFile);
		Path   toFilePath = Paths.get(normalize("" + toFile));

		String relativePath = baseFilePath.relativize(toFilePath).toString();

		relativePath = relativePath.replaceAll("\\\\", "/");
		relativePath = removeLeadingDot(relativePath);

		return relativePath;
	}

	private String removeLeadingDot(String relativePath) {
		if (relativePath.startsWith("."))
			return relativePath.substring(1);
		return relativePath;
	}


}
