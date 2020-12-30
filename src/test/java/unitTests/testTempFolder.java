package unitTests;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static framework.utils.FileUtils.normalize;
import static testHelper.TestHelper.getResourceFile;

public class testTempFolder {

//	@Test
	public void tefst() {
		final String INPUT_ROOT_PATH = "compilerTest_testFiles/D_LINKS/input";

		File baseFile = getResourceFile(INPUT_ROOT_PATH + "/nested/x2Nested/D.extIsIgnored");
		File relFile  = getResourceFile(INPUT_ROOT_PATH + "/nested/C.extIsIgnored");

		Path base = baseFile.toPath();
		Path inRelationTo = Paths.get(normalize("" + relFile));
		String result = base.relativize(inRelationTo).toString();

		System.out.println("==============");
		System.out.println("'" + result + "'");
		System.out.println("==============");
	}
}
