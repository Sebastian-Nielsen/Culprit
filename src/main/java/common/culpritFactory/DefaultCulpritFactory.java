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

import static framework.Constants.Constants.CWD;
import static framework.utils.FileUtils.Filename.areDistinctFilePaths;

public class DefaultCulpritFactory extends Factory implements CulpritFactory{

	public DefaultCulpritFactory(@NotNull String contentRootFolder,
	                             @NotNull String deployRootFolder) {
		super(new ContentFileHierarchy(contentRootFolder),
			  new DeployFileHierarchy(deployRootFolder));
	}

	public DefaultCulpritFactory() {
		this(CWD + "/content", CWD + "/deployment");
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
	public ContentFileHierarchy getContentFileHiearchy() {
		return contentHierarchy;
	}
}
