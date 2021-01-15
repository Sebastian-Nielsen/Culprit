package common.culpritFactory.compilerFactory;

import common.CompilerDataContainer;
import common.CompilerImpl;
import common.PrecompilerImpl;
import framework.*;
import framework.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;

import java.io.File;

public class CustomCompilerFacadeFactory implements CompilerFacadeFactory {

	private final File contentRootFolder;
	private final File deployRootFolder;

	public CustomCompilerFacadeFactory(String contentRootPath, String deployRootPath) {
		this.contentRootFolder = new File(contentRootPath);
		this.deployRootFolder  = new File(deployRootPath);
	}
	public CustomCompilerFacadeFactory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	@Override
	public Precompiler createPrecompiler(CompilerDataContainer compilerDataContainer) {
		return new PrecompilerImpl(compilerDataContainer);
	}

	@Override
	public Compiler createCompiler() {
		return CompilerImpl.getInstance();
	}

	@Override
	public File getContentRootFolder() {
		return contentRootFolder;
	}

	@Override
	public File getDeployRootFolder() {
		return deployRootFolder;
	}
}
