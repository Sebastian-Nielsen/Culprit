package testHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class TestHelper {

//	@NotNull
//	private static String getAbsResourcePath(String name) {
//		return Objects.requireNonNull(getClass().getClassLoader().getResource(name)).getFile();
//	}

	@NotNull
	public static File getResourceFile(String name) {
		return new File(getAbsResourcePath(name));
	}

	@NotNull
	public static String getAbsResourcePath(String name) {
		try {
			URL url = Objects.requireNonNull(TestHelper.class.getClassLoader().getResource(name));
			File file = Paths.get(url.toURI()).toFile();
			return file.getAbsolutePath();
		} catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}


}
