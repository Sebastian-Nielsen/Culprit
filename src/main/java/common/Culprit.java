package common;

import common.fileOption.FileOptionContainer;
import framework.CulpritFactory.CulpritFactory;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.util.Map;

public class Culprit {

	private final CompilerFacade compiler;
	private final PreparatorFacade preparator;
	private final PostEffectFacade postEffects;

	public Culprit(CulpritFactory fac) {
		this.preparator  = new Preparator(fac.createPreparatorFactory());
		this.compiler    = new CompilerFacade(fac.createCompileFactory());
		this.postEffects = new PostEffectFacade(fac.createPostEffectFactory());
	}

	public void compile() throws Exception {
		preparator.prepare();

		Map<File, FileOptionContainer> fileToFOContainer = preparator.extractFOContainerFromEachContentFile();

		Map<File, String> contentFileToHtml = compiler.compile(fileToFOContainer);

		postEffects.effectsFor(contentFileToHtml);
	}

	public void compile(File contentFile) throws Exception {

		FileOptionContainer foContainer = preparator.extractFoContainerFrom(contentFile);

		String html = compiler.compile(contentFile, foContainer);

		postEffects.effectsFor(contentFile, html);
	}

//	/* === Builder Pattern */
//
//	public static class Builder {
//		// === Required parameers
//		/**
// 		 * Factory that contains all necessary dependencies for {@link CompilerFacade}
//		 */
//		private final CompilerFactory compilerDependencyFac;
//
//		private CompilerBuilder
//
//		// === Optional parameters
//		/**
//		 * Whether to add default index.html files to all
//		 * directories that doesn't already have one
//		 */
//		private boolean addDefaultIndexes = true;
//		/**
//		 * Whether to add an ID FileOption to files that doesn't have one
//		 */
//		private boolean addIdToContentFilesWithoutOne = true;
//		/**
//		 * Single {@code File} to compile
//		 */
//		private File compileSingleFile = null;    // CURRENTLY NOT USED, WE JUST CALL .compile(File file);
//		/**
//		 * Whether to prettify html or simply output the semi-prettified html
//		 */
//		private boolean prettifyHtml = false;
//
//		public Builder(CompilerFactory factory) {
//			this.compilerDependencyFac = factory;
//		}
//		public Builder setAddIdToContentFilesWithoutOne(boolean addIdToContentFilesWithoutOne) {
//			this.addIdToContentFilesWithoutOne = addIdToContentFilesWithoutOne;
//			return this;
//		}
//		public Builder setAddDefaultIndexes(boolean shouldAddDefaultIndexes) {
//			this.addDefaultIndexes = shouldAddDefaultIndexes;
//			return this;
//		}
//		public Builder setCompileSingleFile(File file) {
//			this.compileSingleFile = file;
//			return this;
//		}
//		public Builder setPrettifyHtml(boolean bool) {
//			this.prettifyHtml = bool;
//			return this;
//		}
//
//		public Culprit build() {
//			return new Culprit(this);
//		}
//	}

}
