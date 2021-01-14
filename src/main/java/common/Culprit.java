package common;

import framework.CompilerDependencyFactory;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.util.Map;

public class Culprit {

	private final CompilerFacade compiler;
	private final PreparatorFacade preparator;
	private final PostEffectFacade postEffects;

	public Culprit(Builder builder) {

//		builder.preparator;
//		builder.compiler;
//		builder.postEffect;

		this.preparator  = new Preparator();
		this.compiler    = new CompilerFacade();
		this.postEffects = new PostEffectFacade();
	}

	public void compile() throws Exception {
		preparator.prepare();

		Map<File, String> fileToHtml = compiler.compile();

		postEffects.applyFor(fileToHtml);
	}

	public void compile(File contentFile) throws Exception {
		compiler.compile(contentFile);
	}

	/* === Builder Pattern */

	public static class Builder {
		// === Required parameers
		/**
 		 * Factory that contains all necessary dependencies for {@link CompilerFacade}
		 */
		private final CompilerDependencyFactory compilerDependencyFac;

		private CompilerBuilder

		// === Optional parameters
		/**
		 * Whether to add default index.html files to all
		 * directories that doesn't already have one
		 */
		private boolean addDefaultIndexes = true;
		/**
		 * Whether to add an ID FileOption to files that doesn't have one
		 */
		private boolean addIdToContentFilesWithoutOne = true;
		/**
		 * Single {@code File} to compile
		 */
		private File compileSingleFile = null;    // CURRENTLY NOT USED, WE JUST CALL .compile(File file);
		/**
		 * Whether to prettify html or simply output the semi-prettified html
		 */
		private boolean prettifyHtml = false;

		public Builder(CompilerDependencyFactory factory) {
			this.compilerDependencyFac = factory;
		}
		public Builder setAddIdToContentFilesWithoutOne(boolean addIdToContentFilesWithoutOne) {
			this.addIdToContentFilesWithoutOne = addIdToContentFilesWithoutOne;
			return this;
		}
		public Builder setAddDefaultIndexes(boolean shouldAddDefaultIndexes) {
			this.addDefaultIndexes = shouldAddDefaultIndexes;
			return this;
		}
		public Builder setCompileSingleFile(File file) {
			this.compileSingleFile = file;
			return this;
		}
		public Builder setPrettifyHtml(boolean bool) {
			this.prettifyHtml = bool;
			return this;
		}

		public Culprit build() {
			return new Culprit(this);
		}
	}

}
