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

	/**
	 * Compile the specified {@code contentFile} only
	 */
	public String compile(File contentFile) throws Exception {

		String md         = precompiler.compile(contentFile);
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag);  // TODO insert foContainer here as argument

		return htmlTag;
	}


	/* === PRIVATE METHODS */


}
