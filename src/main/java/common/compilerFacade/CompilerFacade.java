package common.compilerFacade;

import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.concreteStrategy.DefaultPageHtmlTemplate;
import framework.compilerFacade.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.compilerFacade.Precompiler;

import java.io.File;

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
	 * Compile the specified {@code contentFile}
	 */
	public String compile(File contentFile) throws Exception {

		String md         = precompiler.compile(contentFile);
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag, dataContainer);

		return htmlTag;
	}



	/* === PRIVATE METHODS */

	private String buildDefaultPageHtmlTemplateUsing(File contentFile,
	                                                 String articleTag,
	                                                 CompilerDataContainer dataContainer) throws Exception {
		return new DefaultPageHtmlTemplate(new HtmlFactory())
				.buildUsing(contentFile, articleTag, dataContainer);
	}


}
