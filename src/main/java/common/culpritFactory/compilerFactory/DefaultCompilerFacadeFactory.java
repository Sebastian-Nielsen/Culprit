package common.culpritFactory.compilerFactory;

import common.compilerFacade.CompilerDataContainer;
import common.compilerFacade.CompilerImpl;
import common.compilerFacade.PrecompilerImpl;
import framework.ContentFileHierarchy;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.DeployFileHierarchy;
import framework.compilerFacade.Compiler;
import framework.compilerFacade.Precompiler;


public class DefaultCompilerFacadeFactory implements CompilerFacadeFactory {

	private final ContentFileHierarchy contentHierarchy;
	private final DeployFileHierarchy  deployHierarchy;

	public DefaultCompilerFacadeFactory(ContentFileHierarchy contentHierarchy, DeployFileHierarchy deployHierarchy) {
		this.contentHierarchy = contentHierarchy;
		this.deployHierarchy  = deployHierarchy;
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
	public ContentFileHierarchy getContentHierarchy() {
		return contentHierarchy;
	}

	@Override
	public DeployFileHierarchy getDeployHierarchy() {
		return deployHierarchy;
	}
}
