package framework.other;

import java.io.IOException;


/**
 * Responsibilities:
 * (1) Reading files
 */
public interface FileHandler {

	/**
	 * Reads the next line of the file focused.
	 * @return the line read
	 */
	String readLine() throws IOException;

	boolean hasNext();

}
