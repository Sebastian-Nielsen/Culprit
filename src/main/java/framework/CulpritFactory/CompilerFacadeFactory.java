package framework.CulpritFactory;

import common.CompilerDataContainer;
import framework.Compiler;
import framework.Precompiler;
import framework.PreparatorFacade;
import framework.singleClasses.CompilerFacade;

import java.io.File;

/**
 * Factory that contains all necessary depdenecies for {@link CompilerFacade}
 */
public interface CompilerFacadeFactory {

	Precompiler createPrecompiler(CompilerDataContainer compilerDataContainer);

	Compiler createCompiler();

	File getContentRootFolder();

	File getDeployRootFolder();

}
