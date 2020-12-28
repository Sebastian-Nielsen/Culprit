package framework;

import java.io.File;

public interface CompilerDependencyFactory {

	Deployer createDeployer();

	Precompiler createPrecompiler();

	Compiler createCompiler();

	File getContentRootFolder();
}
