package common.culpritFactory.compilerFactory;

import common.CompilerDataContainer;
import common.CompilerImpl;
import common.DataExtractor;
import common.PrecompilerImpl;
import framework.*;
import framework.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;

import java.io.File;


public class DefaultCompilerFacadeFactory implements CompilerFacadeFactory {

	private final File contentRootFolder;  // new File(CWD + "/" + "content");
	private final File deployRootFolder;   // new File(CWD + "/" + "deployment");

	public DefaultCompilerFacadeFactory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}

	@Override
	public Precompiler createPrecompiler(CompilerDataContainer dataContainer) {
		return new PrecompilerImpl(dataContainer);
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
