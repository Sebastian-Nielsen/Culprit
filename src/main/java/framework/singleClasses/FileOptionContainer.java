package framework.singleClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static framework.singleClasses.FileOption.KEY;
/**
 * Basically a list of FileOptions, though augmented to also
 * be able to access a FileOption value simply by calling a getter
 * method with a key value. So FileOptionContainer is basically
 * a mix of a list and a map.
 */
public class FileOptionContainer {

	private final Map<KEY, String> keyToVal = new HashMap<>();

	@Override
	public String toString() {
		String s = "";
		return keyToVal.toString();
	}

	@SafeVarargs
	public FileOptionContainer(Entry<KEY, String>... fileOptions) {
		for (Entry<KEY, String> fileOption : fileOptions)
			put(fileOption.getKey(), fileOption.getValue());
	}

	public void put(KEY key, String value) {
		keyToVal.put(key, value);
	}

	public int size() {
		return keyToVal.size();
	}

	public Set<KEY> keySet() {
		return keyToVal.keySet();
	}


	/* GETTERS */

	public String getOrDefault(KEY key, String defaultVal) {
		return keyToVal.getOrDefault(key, defaultVal);
	}

	/**
	 * Retrieve the associated value of {@code key}
	 */
	public String get(KEY key) {
		return keyToVal.get(key);
	}

}
