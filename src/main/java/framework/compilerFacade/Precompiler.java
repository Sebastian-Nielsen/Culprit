package framework.compilerFacade;

import java.io.File;
import java.io.IOException;

public interface Precompiler {

	/**
	 * Precompile the markdown (extracted from {@code contentFile}) into some other markdown.
	 * How? By basically applying the macros as defined by the {@code FileOption}s in the original markdown.
	 * @param contentFile file to extract and compile the markdown of
	 * @return the compiled markdown
	 */
	String compile(File contentFile) throws Exception;

}
