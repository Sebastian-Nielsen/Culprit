package common.compilerSettingsFactories;

import common.CompilerImpl;
import common.PrecompilerImpl;
import common.PreparatorImpl;
import framework.*;
import framework.Compiler;

import java.io.File;

public class CustomCompilerDependencyFactory implements CompilerDependencyFactory {

	private final File contentRootFolder;
	private final File deployRootFolder;

	public CustomCompilerDependencyFactory(String contentRootPath, String deployRootPath) {
		this.contentRootFolder = new File(contentRootPath);
		this.deployRootFolder  = new File(deployRootPath);
	}
	public CustomCompilerDependencyFactory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}
	 
	@Override
	public Preparator createPreparator() {
		return new PreparatorImpl(contentRootFolder, deployRootFolder);
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
