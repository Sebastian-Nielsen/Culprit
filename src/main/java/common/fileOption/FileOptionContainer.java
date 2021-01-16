package common.fileOption;

import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static common.fileOption.FileOption.KEY;
import static common.fileOption.FileOption.REQUIRED_KEYS;

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

	public Set<Entry<KEY, String>> entrySet() { return keyToVal.entrySet(); }

	public boolean containsKey(KEY key) {
		return keyToVal.containsKey(key);
	}

	/* === PRIVATE METHODS */

	private boolean isRequired(KEY key) {
		return FileOption.REQUIRED_KEYS.contains(key);
	}
	private boolean isBool(KEY key) {
		return FileOption.BOOLEAN_KEYS.contains(key);
	}

	/* GETTERS */

	public String getOrDefault(KEY key, String defaultVal) {
		return keyToVal.getOrDefault(key, defaultVal);
	}

	/**
	 * Retrieve the associated value of {@code key}
	 */
	public String get(KEY key) {
		if (isRequired(key))
			return keyToVal.get(key);

		return keyToVal.getOrDefault(key, key.defaultVal);
	}

	/**
	 * Retrieve the associated value of {@code key}
	 */
	public boolean getBoolVal(KEY key) {
		if (isBool(key))
			return Boolean.parseBoolean(keyToVal.getOrDefault(key, key.defaultVal));

		throw new RuntimeException(key + " is not a bool, don't use this method!");
	}

}
