package common;

import framework.CulpritFactory.CompilerFacadeFactory;
import framework.CulpritFactory.CulpritFactory;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;

import java.io.File;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class Culprit {

	private final PreparatorFacade preparator;
	private final PostEffectFacade postEffects;
	private final File contentRootFolder;
	private final DataExtractor dataExtractor;
	private final CompilerFacadeFactory compilerFacadeFactory;

	public Culprit(CulpritFactory fac) {
		super();
		this.dataExtractor = fac.createDataExtractor();
		this.preparator    = new Preparator(fac.createPreparatorFactory());
		this.postEffects   = new PostEffectFacade(fac.createPostEffectFactory());
		this.contentRootFolder = fac.getContentRootFolder();
		this.compilerFacadeFactory = fac.createCompileFacadeFactory();
	}


	public void compileAllFiles() throws Exception {
		compile(listNonDirsFrom(contentRootFolder, RECURSIVE));
	}

	public void compile(File[] files) throws Exception {
		preparator.prepare();

		CompilerFacade compiler = newCompiler(dataExtractor.buildDataContainerForCompiler());

		for (File contentFile : files) {

			String html = compiler.compile(contentFile);

			postEffects.effectsFor(contentFile, html);
		}

	}


	/* === PRIVATE METHODS */

	private CompilerFacade newCompiler(CompilerDataContainer compilerDataContainer) {
		return new CompilerFacade(compilerFacadeFactory, compilerDataContainer);
	}

//	}
//	public void compileAllFiles() throws Exception {
//		preparator.prepare();
//
//		compiler = newCompilerFacade(dataExtractor.extractDataFromAllFiles());
//
//		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE))
//
//			compile(contentFile);
//
//	}
}
