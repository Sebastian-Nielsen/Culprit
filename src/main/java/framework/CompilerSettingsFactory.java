package framework;

import java.io.File;

public interface CompilerSettingsFactory {

	Deployer createDeployer();

	Precompiler createPrecompiler();

	Compiler createCompiler();

	File getContentRootFolder();
}
