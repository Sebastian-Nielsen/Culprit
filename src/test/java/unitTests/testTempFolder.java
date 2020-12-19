package unitTests;

import org.junit.jupiter.api.io.TempDir;

import java.io.*;

public class testTempFolder {

	private ByteArrayOutputStream fileWriter;

//	@Test
	public void tefst(@TempDir File tempDir) throws Exception {
		File file = new File(tempDir + "/file.txt");

		System.out.println();
		System.out.println();
		System.out.println(tempDir.exists());
		System.out.println();
		System.out.println();

		FileWriter myWriter = new FileWriter(file);
		myWriter.write("test");
		myWriter.close();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		System.out.println(reader.readLine());
		reader.close();

//		System.out.println(tempDir.exists());
//		System.out.println(tempDir);
//
//		File fi   = new File(tempDir + "/okay.txt");
//		File f    = new File(tempDir + "/yo.txt");
//		file.createNewFile();
//		fi.createNewFile();
//		f.createNewFile();
//
//		System.out.println();
//		System.out.println();
//
//		System.out.println(file + " " + file.exists());
//		System.out.println(fi + " " + fi.exists());
//		System.out.println(f + " " + f.exists());
//
//		System.out.println();
//		System.out.println(tempDir.listFiles().length);
//		System.out.println();
//		System.out.println("===");
//		Arrays.stream(tempDir.listFiles()).forEach(System.out::println);
//		System.out.println("===");
//		System.out.println();
//		System.out.println();


//	    FileHandler f = new FileHandlerImpl(tempDir);


	    // assert
//	    assertTrue(Files.exists(output));
//	    assertLinesMatch(List.of("test"), Files.readAllLines(output));
	}
}
