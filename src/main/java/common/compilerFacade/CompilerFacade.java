package common.compilerFacade;

import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.concreteStrategy.DefaultPageHtmlTemplate;
import framework.compilerFacade.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.compilerFacade.Precompiler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CompilerFacade {

	private @NotNull final Precompiler precompiler;
	private @NotNull final Compiler compiler;
	private @NotNull final CompilerDataContainer dataContainer;

	public CompilerFacade(@NotNull CompilerFacadeFactory factory,
	                      @NotNull CompilerDataContainer dataContainer) {

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
		String htmlTag    = buildDefaultPageUsing(contentFile, articleTag, dataContainer);

		return htmlTag;
	}



	/* === PRIVATE METHODS */

	private String buildDefaultPageUsing(File contentFile,
	                                     String articleTag,
	                                     CompilerDataContainer dataContainer) throws Exception {
		return new DefaultPageHtmlTemplate(new HtmlFactory())
				.buildUsing(contentFile, articleTag, dataContainer);
	}


}
