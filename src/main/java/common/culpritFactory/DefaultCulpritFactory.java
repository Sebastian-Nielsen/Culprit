package common.culpritFactory.compilerFactory;

import common.culpritFactory.DefaultPreparatorFactory;
import common.culpritFactory.DefaultPostEffectFactory;
import framework.CulpritFactory.CompilerFactory;
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
	public CompilerFactory createCompileFactory() {
		return new DefaultCompilerFactory(contentRootFolder, deployRootFolder);
	}

	@Override
	public DefaultPostEffectFactory createPostEffectFactory() {
		return new DefaultPostEffectFactory(contentRootFolder, deployRootFolder);
	}
}
