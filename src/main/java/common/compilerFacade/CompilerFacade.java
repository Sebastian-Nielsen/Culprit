package common.compilerFacade;

import framework.compilerFacade.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.compilerFacade.Precompiler;

import java.io.File;

import static common.html.htmlTemplatesStrategy.Helper.buildDefaultPageHtmlTemplateUsing;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class CompilerFacade {

	private final Precompiler precompiler;
	private final Compiler compiler;
	private final CompilerDataContainer dataContainer;

	public CompilerFacade(CompilerFacadeFactory factory, CompilerDataContainer dataContainer) {
		this.precompiler   = factory.createPrecompiler(dataContainer);
		this.compiler      = factory.createCompiler();
		this.dataContainer = dataContainer;
	}

	/**
	 * Compile the specified {@code contentFile} only
	 */
	public String compile(File contentFile) throws Exception {

		String md         = precompiler.compile(contentFile);
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag, dataContainer);

		return htmlTag;
	}

}
