package common;

import common.compilerFacade.CompilerFacade;
import common.culpritFactory.DefaultPostEffectFactory;
import common.preparatorFacade.Preparator;
import framework.ContentFileHierarchy;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.CulpritFactory.CulpritFactory;
import framework.PreparatorFacade;

import java.io.File;
import java.nio.file.Files;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class Culprit {

	private final PreparatorFacade preparator;
	private final ContentFileHierarchy contentHierarchy;
	private final DataExtractor dataExtractor;
	private final CompilerFacadeFactory compilerFacadeFactory;
	private final DefaultPostEffectFactory postEffectFactory;

	public Culprit(CulpritFactory fac) {
		super();

		this.preparator    = new Preparator(fac.createPreparatorFactory());
		this.dataExtractor = fac.createDataExtractor();

		this.contentHierarchy = fac.getContentFileHiearchy();

		this.compilerFacadeFactory = fac.createCompileFacadeFactory();
		this.postEffectFactory     = fac.createPostEffectFactory();
	}


	public void compileAllFiles() throws Exception {
		File[]  files = contentHierarchy.listNonDirs(RECURSIVELY);
		compile(files);
	}

	public void compile(File[] files) throws Exception {
		prepare();

		CompilerFacade    compiler    = newCompiler();
		PostEffectsFacade postEffects = newPostEffects();

		compileAndApplyEffectsFor(files, compiler, postEffects);

		postEffects.afterEffects();
	}

	private void prepare() throws Exception {
		preparator.prepare();
		dataExtractor.buildDataContainers();
	}



	/* === PRIVATE METHODS */

	private PostEffectsFacade newPostEffects() {
		return new PostEffectsFacade(
				postEffectFactory,
				dataExtractor.getPostEffectDataContainer()
		);
	}

	private CompilerFacade newCompiler() {
		return new CompilerFacade(
				compilerFacadeFactory,
				dataExtractor.getCompilerDataContainer()
		);
	}

	private void compileAndApplyEffectsFor(File[] files, CompilerFacade compiler, PostEffectsFacade postEffects) throws Exception {

		for (File contentFile : files) {

			String html = compiler.compile(contentFile);

			postEffects.effectsFor(contentFile, html);
		}
	}

}
