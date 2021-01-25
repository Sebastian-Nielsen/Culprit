package common.compilerFacade;

import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;
import framework.compilerFacade.Precompiler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.fileOption.FileOption.KEY.D_LINKS;
import static common.fileOption.FileOption.KEY.TOC;
import static common.other.ValidatorImpl.REGEXES;
import static framework.utils.FileUtils.Filename.*;
import static framework.utils.FileUtils.Retriever.contentOf;

public class PrecompilerImpl implements Precompiler {  // TODO !IMPORTANT! THIS CLASS HAS SERIOUS DESIGN ISSUES 

	private @NotNull final CompilerDataContainer dataContainer;

	public PrecompilerImpl(@NotNull CompilerDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public String compile(File contentFile) throws Exception {

		FileOptionContainer foContainer = dataContainer.getFOContainerOf(contentFile);

		String contentOfFileToCompile = contentOf(contentFile);

		// === Third, handle all the fileoptions; apply them on the retrieved content
		contentOfFileToCompile = compileDLinks(foContainer, contentOfFileToCompile, contentFile);
		contentOfFileToCompile = compileBackslashes(contentOfFileToCompile);
//		contentOfFileToCompile = compileTOC(foContainer, contentOfFileToCompile, contentFile);
		// ===

		return contentOfFileToCompile;
	}



	/* === PRIVATE METHODS */

	/**
	 * Compile Table Of Content (TOC)
	 */
//	private String compileTOC(FileOptionContainer foContainer, String contentOfFileToCompile, File contentFile) {
//		String TOC_val = foContainer.getOrDefault(TOC, TOC.defaultVal);
//
//		if (TOC.equals("false"))
//			return contentOfFileToCompile;
//
//
//
//		return "";
//	}

	/**
	 * `\\\\` (in an .md) file will compile into `\\` (in a .html file).
	 * This method ensures `\\\\` (.md) compiles to `\\\\` (.html)
	 * by precompiling all `\\\\` (.md) into `\\\\\\\\` (.md).
	 */
	private String compileBackslashes(String content) {
		return content.replace("\\", "\\\\");
	}

	/**
	 * For each dynamic-link in {@code fileToCompileContent}, replace
	 * the ID in the dynamic-link with <u>the relative path
	 * from the current <em>contentFile</em>
	 * to   the <em>file assocated with the ID</em></u>.
	 * <p>
	 * E.g.
	 * <pre>
	 *      `[link text](2d424a8f-6fe8-455d-81de-6be20691cf32)`
	 *          ->
	 *      `[link text](./../folder/Z.md)`
	 * </pre>
	 * @param foContainer the value of the D_LINKS-key.
	 */
	private String compileDLinks(FileOptionContainer foContainer, String contentOfFileToCompile, File contentFile) {
		String DLINKS_val = foContainer.getOrDefault(D_LINKS, D_LINKS.defaultVal);

		if (DLINKS_val.equals("false"))
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
