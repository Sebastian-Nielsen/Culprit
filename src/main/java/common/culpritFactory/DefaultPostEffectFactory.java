package common.culpritFactory;

import framework.ContentFileHierarchy;
import framework.CulpritFactory.Factory;
import framework.DeployFileHierarchy;

import java.io.File;

public class DefaultPostEffectFactory extends Factory {

	public DefaultPostEffectFactory(ContentFileHierarchy contentHierarchy, DeployFileHierarchy deployHierarchy) {
		super(contentHierarchy, deployHierarchy);
	}

	public boolean shouldPrettifyHtml() {
		return true;
	}

}
