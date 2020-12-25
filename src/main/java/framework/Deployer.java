package framework;

import java.io.IOException;

/**
 * Responsibilities:
 * (1) Copy the file-hiearachy from *content* to *ioFiles.deployment*:
 * +--------------- For all files in *content*, do: ----------------+
 * | Given e.g.                                                     |
 * |          File("C:/.../content/{relativePath}/test.md")         |
 * | Then create                                                    |
 * |          File("C:/.../ioFiles.deployment/{relativePath}/test.html")    |
 * +----------------------------------------------------------------+
 */
public interface Deployer {

	/* Copy the file-hiearachy from *content* to *ioFiles.deployment* */
	void deploy() throws IOException;

	/**
	 * Creates an `index.html` file for each folder in *ioFiles.deployment* that doesn't already have one.
	 */
	void addDefaultIndexesTo();
}
