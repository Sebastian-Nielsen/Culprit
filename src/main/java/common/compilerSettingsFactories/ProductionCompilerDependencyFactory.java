package common.compilerSettingsFactories;

import common.CompilerImpl;
import common.PrecompilerImpl;
import common.Preparator;
import framework.*;
import framework.Compiler;

import java.io.File;

import static framework.Constants.Constants.CWD;


public class ProductionCompilerDependencyFactory implements CompilerDependencyFactory {

	private final File contentRootFolder = new File(CWD + "/" + "content");
	private final File deployRootFolder  = new File(CWD + "/" + "deployment");

	@Override
	public PreparatorFacade createPreparator() {
		return new Preparator(contentRootFolder, deployRootFolder);
	}

	@Override
	public Precompiler createPrecompiler() {
		return new PrecompilerImpl(contentRootFolder);
	}

	@Override
	public Compiler createCompiler() {
		return CompilerImpl.getInstance();
	}

	@Override
	public File getContentRootFolder() {
		return contentRootFolder;
	}

}
