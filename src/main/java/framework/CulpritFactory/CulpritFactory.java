package framework.CulpritFactory;

import common.DataExtractor;
import common.culpritFactory.DefaultPostEffectFactory;
import framework.ContentFileHierarchy;

public interface CulpritFactory {

	PreparatorFactory createPreparatorFactory();

	DataExtractor createDataExtractor();

	CompilerFacadeFactory createCompileFacadeFactory();

	DefaultPostEffectFactory createPostEffectFactory();

	ContentFileHierarchy getContentFileHiearchy();
}
