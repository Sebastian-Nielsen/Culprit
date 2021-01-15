package framework.CulpritFactory;

import common.DataExtractor;
import common.culpritFactory.DefaultPostEffectFactory;

import java.io.File;

public interface CulpritFactory {

	abstract PreparatorFactory createPreparatorFactory();

	abstract DataExtractor createDataExtractor();

	abstract CompilerFactory createCompileFactory();

	abstract DefaultPostEffectFactory createPostEffectFactory();

	abstract File getContentRootFolder();
}
