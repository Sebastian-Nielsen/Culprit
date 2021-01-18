package common;

import common.html.NavigationHtmlGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Immutable container for all data that the {@link PostEffectsFacade}
 * needs in order to carry out all its effects.
 */
public class PostEffectDataContainer {

	private @NotNull final NavigationHtmlGenerator navHtmlGenerator;

	public PostEffectDataContainer(@NotNull NavigationHtmlGenerator navHtmlGenerator) {

		this.navHtmlGenerator = navHtmlGenerator;
	}

	public String getNavigationHtmlOf(File deployFile) {
		return navHtmlGenerator.getNavHtmlOf(deployFile);
	}

}
