package common.culpritFactory;

import common.DataExtractor;
import common.culpritFactory.compilerFactory.DefaultCompilerFacadeFactory;
import framework.CulpritFactory.CompilerFacadeFactory;
import framework.CulpritFactory.CulpritFactory;
import framework.CulpritFactory.Factory;
import framework.CulpritFactory.PreparatorFactory;

import java.io.File;

import static framework.Constants.Constants.CWD;

public class DefaultCulpritFactory extends Factory implements CulpritFactory{

	public DefaultCulpritFactory(File contentRootFolder, File deployRootFolder) {
		super(contentRootFolder, deployRootFolder);
	}

	public DefaultCulpritFactory() {
		super(new File(CWD + "/content"), new File(CWD + "/deployment"));
	}

	@Override
	public PreparatorFactory createPreparatorFactory() {
		return new DefaultPreparatorFactory(contentRootFolder, deployRootFolder);
	}

	@Override
	public DataExtractor createDataExtractor() {
		return new DataExtractor(contentRootFolder, deployRootFolder);
	}

	@Override
	public CompilerFacadeFactory createCompileFacadeFactory() {
		return new DefaultCompilerFacadeFactory(contentRootFolder, deployRootFolder);
	}

	@Override
	public DefaultPostEffectFactory createPostEffectFactory() {
		return new DefaultPostEffectFactory(contentRootFolder, deployRootFolder);
	}
}
