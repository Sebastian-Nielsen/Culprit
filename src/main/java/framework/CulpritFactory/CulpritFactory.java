package framework.CulpritFactory;

import common.culpritFactory.DefaultPostEffectFactory;

import java.io.File;

public interface CulpritFactory {

	abstract PreparatorFactory createPreparatorFactory();

	abstract CompilerFactory   createCompileFactory();

	abstract DefaultPostEffectFactory createPostEffectFactory();

}
