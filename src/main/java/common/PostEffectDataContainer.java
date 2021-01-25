package common;

import common.html.NavigationHtmlGenerator;
import framework.DeployFileHierarchy;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Immutable container for all data that the {@link PostEffectsFacade}
 * needs in order to carry out all its effects.
 */
public class PostEffectDataContainer {

	private @NotNull final NavigationHtmlGenerator navHtmlGenerator;
	private @NotNull final DeployFileHierarchy deployHierarchy;

	public PostEffectDataContainer(@NotNull DeployFileHierarchy deployHierarchy,
	                               @NotNull NavigationHtmlGenerator navHtmlGenerator) {
		this.deployHierarchy = deployHierarchy;
		this.navHtmlGenerator = navHtmlGenerator;
	}

	@NotNull
	public String getNavigationHtmlOf(String relFilePathWithoutExt) {
		return navHtmlGenerator.getNavHtmlOf(relFilePathWithoutExt);
	}

	public File getDeployRootDir() {
		return deployHierarchy.getRootDir();
	}

}
