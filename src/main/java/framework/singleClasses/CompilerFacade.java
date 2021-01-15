package framework.singleClasses;

import common.CompilerDataContainer;
import common.fileOption.FileOptionContainer;
import framework.Compiler;
import framework.*;
import framework.CulpritFactory.CompilerFacadeFactory;

import java.io.File;

import static common.html.concreteHtmlTemplates.Helper.buildDefaultPageHtmlTemplateUsing;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class CompilerFacade {

	private final Precompiler precompiler;
	private final Compiler compiler;

	public CompilerFacade(CompilerFacadeFactory factory, CompilerDataContainer dataContainer) {
		this.precompiler   = factory.createPrecompiler(dataContainer);
		this.compiler      = factory.createCompiler();
	}

//	/**
//	 * Compile all files in {@code contentRootfolder}
//	 * @return a map of each content-file pointing to its html (=compiled markdown)
//	 */
//	public Map<File, String> compile(Map<File, FileOptionContainer> fileToFOContainer) throws Exception {
//
//		Map<File, String> contentFileToMd   = precompiler.compileAllFiles(fileToFOContainer);
//
//		Map<File, String> contentFileToHtml =    compiler.compileAllFiles(contentFileToMd, fileToFOContainer);
//		// TODO: this method should also take in {@code fileToFOContainer} ^^
//
//		return contentFileToHtml;
//	}

	/**
	 * Compile the specified file only
	 * @param foContainer {@code FileOptionContainer} of the specified {@code contentFile}
	 */
	public String compile(File contentFile) throws Exception {

		String md         = precompiler.compile(contentFile);
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag);  // TODO insert foContainer here as argument

		return htmlTag;
	}


	/* === PRIVATE METHODS */


}
