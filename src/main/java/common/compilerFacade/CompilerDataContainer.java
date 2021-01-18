package common.compilerFacade;

import common.fileOption.FileOptionContainer;
import common.html.NavigationHtml;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.util.Map;

/**
 * Immutable container for all data that the {@link CompilerFacade} needs in
 * order to compile a given .md {@code File}.
 */
public class CompilerDataContainer {

	private @Unmodifiable @NotNull final Map<String, File>                idToContentFile;
	private @Unmodifiable @NotNull final Map<String, FileOptionContainer> pathToFOContainer;
	private               @NotNull final NavigationHtml                   navigationHtml;

	public CompilerDataContainer(@NotNull Map<String, File>                idToContentFile,
	                             @NotNull Map<String, FileOptionContainer> pathToFOContainer,
								 @NotNull NavigationHtml                   navigationHtml) {

		this.idToContentFile   = idToContentFile;
		this.pathToFOContainer = pathToFOContainer;
		this.navigationHtml    = navigationHtml;
	}


	/* === Getters */

	public FileOptionContainer getFOContainerOf(File file) {
		return pathToFOContainer.get(file.toString());
	}

	public File getFileOfId(String id) {
		return idToContentFile.get(id);
	}

	public String getNavigationHtmlOf(File file) {
		return navigationHtml.getNavHtmlOf(file);
	}

}
