package framework.CulpritFactory;

import common.compilerFacade.CompilerDataContainer;
import common.compilerFacade.CompilerFacade;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import framework.compilerFacade.Compiler;
import framework.compilerFacade.Precompiler;

import java.io.File;

/**
 * Factory that contains all necessary depdenecies for {@link CompilerFacade}
 */
public interface CompilerFacadeFactory {

	Precompiler createPrecompiler(CompilerDataContainer compilerDataContainer);

	Compiler createCompiler();

	ContentFileHierarchy getContentHierarchy();

	DeployFileHierarchy getDeployHierarchy();

}
