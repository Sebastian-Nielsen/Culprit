package common.culpritFactory;

import framework.CulpritFactory.Factory;

import java.io.File;

public class DefaultPostEffectFactory extends Factory {

	public DefaultPostEffectFactory(File contentRootFolder, File deployRootFolder) {
		super(contentRootFolder, deployRootFolder);
	}

	public boolean shouldPrettifyHtml() {
		return true;
	}

}
