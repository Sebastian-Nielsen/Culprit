package common.compilerFacade;

import common.fileOption.FileOptionContainer;
import common.html.NavigationHtmlGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.util.Map;

/**
 * Immutable container for all data that the {@link CompilerFacade} needs in
 * order to compile a given .md {@code File}.
 */
public class CompilerDataContainer {   // TODO: this container should contain subcontainers, like "PrecompilerDataContainer"

	private @Unmodifiable @NotNull final Map<String, File>                idToContentFile;
	private @Unmodifiable @NotNull final Map<String, FileOptionContainer> pathToFOContainer;
	private               @NotNull final NavigationHtmlGenerator          navHtmlGenerator;

	private @NotNull final File contentRootFolder;
	private @NotNull final File deployRootFolder;


	public CompilerDataContainer(@NotNull Map<String, File>                idToContentFile,
	                             @NotNull Map<String, FileOptionContainer> pathToFOContainer,
	                             @NotNull NavigationHtmlGenerator navHtmlGenerator,
	                             @NotNull File contentRootFolder,
	                             @NotNull File deployRootFolder) {

		this.idToContentFile   = idToContentFile;
		this.pathToFOContainer = pathToFOContainer;
		this.navHtmlGenerator  = navHtmlGenerator;

		this.contentRootFolder = contentRootFolder;
		this.deployRootFolder  = deployRootFolder;
	}



	/* === Getters */

	public FileOptionContainer getFOContainerOf(File file) {
		return pathToFOContainer.get(file.toString());
	}

	public File getFileOfId(String id) {
		return idToContentFile.get(id);
	}

	public String getNavigationHtmlOf(File deployFile) {
		return navHtmlGenerator.getNavHtmlOf(deployFile);   //TODO does it really have to be a deploy file? (make it more general)
	}


	public @NotNull File getContentRootFolder() {
		return contentRootFolder;
	}

	public @NotNull File getDeployRootFolder() {
		return deployRootFolder;
	}


}
