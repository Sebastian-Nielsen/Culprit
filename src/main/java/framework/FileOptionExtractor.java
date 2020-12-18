package framework;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileOptionExtractor {
	List<String> extractFileOptionsFrom(File file) throws IOException;
}
