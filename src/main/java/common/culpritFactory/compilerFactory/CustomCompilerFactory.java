package common.culpritFactory.compilerFactory;

import common.CompilerImpl;
import common.PrecompilerImpl;
import framework.*;
import framework.Compiler;
import framework.CulpritFactory.CompilerFactory;

import java.io.File;

public class CustomCompilerFactory implements CompilerFactory {

	private final File contentRootFolder;
	private final File deployRootFolder;

	public CustomCompilerFactory(String contentRootPath, String deployRootPath) {
		this.contentRootFolder = new File(contentRootPath);
		this.deployRootFolder  = new File(deployRootPath);
	}
	public CustomCompilerFactory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
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

	@Override
	public File getDeployRootFolder() {
		return deployRootFolder;
	}
}
