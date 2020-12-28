package common.compilerSettingsFactories;

import common.CompilerImpl;
import common.PrecompilerImpl;
import common.DeployerImpl;
import framework.*;
import framework.Compiler;

import java.io.File;

public class CustomCompilerSettingsFactory implements CompilerSettingsFactory {

	private final File contentRootFolder;
	private final File deployRootFolder;

	public CustomCompilerSettingsFactory(String contentRootPath, String deployRootPath) {
		this.contentRootFolder = new File(contentRootPath);
		this.deployRootFolder  = new File(deployRootPath);
	}
	public CustomCompilerSettingsFactory(File contentRootFolder, File deployRootFolder) {
		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}
	 
	@Override
	public Deployer createDeployer() {
		return new DeployerImpl(contentRootFolder, deployRootFolder);
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
