package common.culpritFactory.compilerFactory;

import common.compilerFacade.CompilerDataContainer;
import common.compilerFacade.CompilerImpl;
import common.compilerFacade.PrecompilerImpl;
import framework.compilerFacade.Compiler;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.compilerFacade.Precompiler;

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
