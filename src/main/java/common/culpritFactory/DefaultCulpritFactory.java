package common.culpritFactory;

import common.DataExtractor;
import common.culpritFactory.compilerFactory.DefaultCompilerFacadeFactory;
import framework.ContentFileHierarchy;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.CulpritFactory.CulpritFactory;
import framework.CulpritFactory.Factory;
import framework.CulpritFactory.PreparatorFactory;
import framework.DeployFileHierarchy;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.AbstractDocument;
import java.io.File;

import static framework.Constants.Constants.CWD;

public class DefaultCulpritFactory extends Factory implements CulpritFactory{

	public DefaultCulpritFactory(@NotNull String contentRootFolder,
	                             @NotNull String deployRootFolder) {
		super(new ContentFileHierarchy(contentRootFolder),
			  new DeployFileHierarchy(deployRootFolder));
	}

	public DefaultCulpritFactory() {
		super(new ContentFileHierarchy(CWD + "/content"),
			  new DeployFileHierarchy( CWD + "/deployment"));
	}

	@Override
	public PreparatorFactory createPreparatorFactory() {
		return new DefaultPreparatorFactory(contentHierarchy, deployHierarchy);
	}

	@Override
	public DataExtractor createDataExtractor() {
		return new DataExtractor(contentHierarchy, deployHierarchy);
	}

	@Override
	public CompilerFacadeFactory createCompileFacadeFactory() {
		return new DefaultCompilerFacadeFactory(contentHierarchy, deployHierarchy);
	}

	@Override
	public DefaultPostEffectFactory createPostEffectFactory() {
		return new DefaultPostEffectFactory(contentHierarchy, deployHierarchy);
	}

	@Override
	public File getContentRootFolder() {
		return contentHierarchy.getContentRootDir();
	}
}
