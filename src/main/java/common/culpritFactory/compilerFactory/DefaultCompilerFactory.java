package common.culpritFactory.compilerFactory;

import common.CompilerImpl;
import common.PrecompilerImpl;
import common.Preparator;
import framework.*;
import framework.Compiler;
import framework.CulpritFactory.CompilerFactory;
import framework.singleClasses.CompilerFacade;

import java.io.File;


public class DefaultCompilerFactory implements CompilerFactory {

	private final File contentRootFolder;  // new File(CWD + "/" + "content");
	private final File deployRootFolder;   // new File(CWD + "/" + "deployment");

	public DefaultCompilerFactory(File contentRootFolder, File deployRootFolder) {
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
