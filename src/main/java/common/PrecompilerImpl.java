package common;

import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;
import framework.Precompiler;
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

import static common.DataExtractor.extractFoContainerFrom;
import static common.fileOption.FileOption.KEY;
import static common.fileOption.FileOption.KEY.ID;
import static framework.singleClasses.ValidatorImpl.REGEXES;
import static framework.utils.FileUtils.Filename.*;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Retriever.contentOf;

public class PrecompilerImpl implements Precompiler {
	private final CompilerDataContainer dataContainer;

	public PrecompilerImpl() {
		this(null); // TODO: it shouldn't be null here but a stub for testing?
	}
	public PrecompilerImpl(CompilerDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

//	public Map<File, String> compileAllFiles(Map<File, FileOptionContainer> fileToFOContainer) throws IOException {
//		preprocess(fileToFOContainer);
//
//		Map<File, String> fileToCompiledContent = new HashMap<>();
//
//		for (File file : listNonDirsFrom(contentRootFolder, RECURSIVE))
//
//			fileToCompiledContent.put(
//					file,
//					compile(file, fileToFOContainer.get(file))
//			);
//
//		return fileToCompiledContent;
//	}

	@Override
	public String compile(File contentFile) throws IOException {
		FileOptionContainer foContainer = dataContainer.getFOContainerOf(contentFile);

		// First, retrieve the fileoption values from the foContainer
		String     ID_val = foContainer.get(KEY.ID); // Required FileOption
		String DLINKS_val = foContainer.getOrDefault(KEY.D_LINKS, "false");

		// Second, retrieve the content of the fileToCompile
		String content = contentOf(contentFile);

		// Third, handle all the fileoptions; apply them on the retrieved content
		content = handleDLINKS(DLINKS_val, content, contentFile);

		// return the content
		return content;
	}


	/* === PRIVATE METHODS */

	/**
	 * For each dynamic-link in {@code fileToCompileContent}, replace
	 * the ID with the relative path of the file associated with the ID,
	 * relativized in relation to {@code contentFile}.
	 *  E.g.    `[link text](2d424a8f-6fe8-455d-81de-6be20691cf32)`
	 *              ->
	 *          `[link text](../folder/Z.md)`
	 * @param shouldHandleDLINKS the value of the D_LINKS-key.
	 */
	private String handleDLINKS(String shouldHandleDLINKS, String contentOfFileToCompile, File contentFile) {
		if (shouldHandleDLINKS.equals("false"))
			return contentOfFileToCompile;

		String markdownLinkRegex = "\\[(.*?)]\\((%s)\\)".formatted(REGEXES.UUID);
		Matcher matcher = Pattern.compile(markdownLinkRegex).matcher(contentOfFileToCompile);

		boolean isDLinksInFileToCompile = matcher.find();
		if (!isDLinksInFileToCompile)
			return contentOfFileToCompile;

		return replaceAllIDsWithFilePaths(matcher, contentFile);
	}

	private String replaceAllIDsWithFilePaths(Matcher matcher, File contentFile) {
		StringBuffer buffer = new StringBuffer();
		do {
			String linkText = matcher.group(1);
			String id       = matcher.group(2);
			File   fileOfId = dataContainer.getFileOfId(id);
			String relDeployPath = relativeFilePathBetween(contentFile, fileOfId);
			String replacement = "[" + linkText + "](" + changeFileExt(relDeployPath, "html") + ")";

			matcher.appendReplacement(buffer, replacement);
		} while (matcher.find());

		matcher.appendTail(buffer);

		return buffer.toString();
	}



//	/**
//	 * Go through all files while:
//	 * (1) Asserting each {@code File} has all _required_ {@code FileOption}s
//	 * (2) Store ({@code ID}, deploy-{@code File}) pair in map
//	 */
//	private void preprocess(Map<File, FileOptionContainer> fileToFOContainer) {  // TODO: Move this to Preparator
//
//		Set<File> files = fileToFOContainer.keySet();
//		for (File file : files) {
//
//			if (!file.getName().endsWith(".md")) continue;
//
//			assertFileHasAllRequiredFileOptions(file, fileToFOContainer);
//		}
//	}
//
//	private void assertFileHasAllRequiredFileOptions(File file, Map<File, FileOptionContainer> fileToFOContainer) {
//
//		Set<KEY> keysOfFile = fileToFOContainer.get(file).keySet();
//
//		for (KEY key : FileOption.REQUIRED_KEYS) {
//
//			boolean fileHasRequiredFileOption = keysOfFile.contains(key);
//			if (!fileHasRequiredFileOption)
//				throw new RuntimeException(
//						"For File: " + file + "\n" +
//						"For KEY: "  + key  + "\n" +
//						"no value has been given and there's no default value.\n\n" +
//						"Please add FileOption: `["+key+"]:<> (<value>)´\n" +
//						"to File: " + file + "\n\n");
//		}
//
//	}






}
