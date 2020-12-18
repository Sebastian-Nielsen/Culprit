package framework;

import java.io.File;
import java.io.IOException;


/**
 * Responsibilities:
 * (1) Reading files
 */
public interface FileHandler {

	/**
	 * Alters the state of the {@link FileHandler} to now read from {@code file}.
	 * Sideeffect: Reading of the former file focused (if any) is closed.
	 * @param file
	 */
//	void focus(File file) throws IOException;

	/**
	 * Reads the next line of the file focused.
	 * @return the line read
	 */
	String readLine() throws IOException;

	boolean hasNext();

}
