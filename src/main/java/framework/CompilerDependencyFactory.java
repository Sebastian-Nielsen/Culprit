package framework;

import framework.singleClasses.CompilerFacade;

import java.io.File;

/**
 * Factory that contains all necessary depdenecies for {@link CompilerFacade}
 */
public interface CompilerDependencyFactory {

	Preparator createPreparator();

	Precompiler createPrecompiler();

	Compiler createCompiler();

	File getContentRootFolder();
}
