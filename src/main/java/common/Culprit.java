package common;

import common.fileOption.FileOptionContainer;
import framework.CulpritFactory.CulpritFactory;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;
import framework.utils.FileUtils;

import java.io.File;
import java.util.Map;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class Culprit {

	private final CompilerFacade compiler;
	private final PreparatorFacade preparator;
	private final PostEffectFacade postEffects;
	private final File contentRootFolder;
	private final DataExtractor dataExtractor;

	public Culprit(CulpritFactory fac) {
		super();
		this.preparator    = new Preparator(fac.createPreparatorFactory());
		this.dataExtractor = fac.createDataExtractor();
		this.compiler      = new CompilerFacade(fac.createCompileFactory());
		this.postEffects   = new PostEffectFacade(fac.createPostEffectFactory());
		this.contentRootFolder = fac.getContentRootFolder();
	}

	public void execute() throws Exception {
		preparator.prepare();

		dataExtractor.getIdToDeployFile();

		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {

			FileOptionContainer foContainer = dataExtractor.extractFoContainerFrom(contentFile);

			String html = compiler.compile(contentFile, foContainer);

			postEffects.effectsFor(contentFile, html);
		}

	}

	public void compile(File contentFile) throws Exception {

		FileOptionContainer foContainer = dataExtractor.extractFoContainerFrom(contentFile);

		String html = compiler.compile(contentFile, foContainer);

		postEffects.effectsFor(contentFile, html);
	}

}
