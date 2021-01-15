package framework;

import common.fileOption.FileOptionContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Precompiler {

	/**
	 * Precompile the markdown (extracted from {@code contentFile}) into some other markdown.
	 * How? By basically applying the macros as defined by the {@code FileOption}s in the original markdown.
	 * @param contentFile file to extract and compile the markdown of
	 * @return the compiled markdown
	 */
	String compile(File contentFile) throws IOException;

}
