//package common.culpritFactory.compilerFactory;
//
//import common.compilerFacade.CompilerDataContainer;
//import common.compilerFacade.CompilerImpl;
//import common.compilerFacade.PrecompilerImpl;
//import framework.ContentFileHierarchy;
//import framework.DeployFileHierarchy;
//import framework.compilerFacade.Compiler;
//import framework.CulpritFactory.CompilerFacadeFactory;
//import framework.compilerFacade.Precompiler;
//
//import java.io.File;
//
//public class CustomCompilerFacadeFactory implements CompilerFacadeFactory {
//
//	private final File contentRootFolder;
//	private final File deployRootFolder;
//
//	public CustomCompilerFacadeFactory(String contentRootPath, String deployRootPath) {
//		this.contentRootFolder = new File(contentRootPath);
//		this.deployRootFolder  = new File(deployRootPath);
//	}
//	public CustomCompilerFacadeFactory(File contentRootFolder, File deployRootFolder) {
//		this.contentRootFolder = contentRootFolder;
//		this.deployRootFolder  = deployRootFolder;
//	}
//
//	@Override
//	public Precompiler createPrecompiler(CompilerDataContainer compilerDataContainer) {
//		return new PrecompilerImpl(compilerDataContainer);
//	}
//
//	@Override
//	public Compiler createCompiler() {
//		return CompilerImpl.getInstance();
//	}
//
//	@Override
//	public ContentFileHierarchy getContentHierarchy() {
//		return contentRootFolder;
//	}
//
//	@Override
//	public DeployFileHierarchy getDeployHierarchy() {
//		return deployRootFolder;
//	}
//}
