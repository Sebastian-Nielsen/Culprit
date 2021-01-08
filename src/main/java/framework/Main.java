package framework;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import common.CompilerImpl;
import common.compilerSettingsFactories.ProductionCompilerDependencyFactory;
import common.html.HTML;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.HREF;
import static framework.Constants.Constants.CWD;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.deleteDirectory;


public class Main {

	private static final Map<String, Boolean> argToVal = new HashMap<>();

	private static void pasreArgs(String[] args) {
//		assert args.length % 2 == 0;
		System.out.println("new version");
		if (args.length == 0)
			return;

		String arg = null;
		boolean value;
		for (int i = 0; i < args.length; i++) {
			if (i % 2 == 0)
				arg = args[i];
			else
				argToVal.put(arg, Boolean.valueOf(args[i]));
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("length of args: " + args.length);

		CompilerFacade compiler =
			new CompilerFacade
				.Builder(new ProductionCompilerDependencyFactory())
				.setAddDefaultIndexes(true)
				.setAddIdToContentFilesWithoutOne(true)
				.setPrettifyHtml(true)
				.build();

		if (args.length > 0)
			compileSingleFile(compiler, args);
		else {
			System.out.println("+--------DEBUG---------------+");
			System.out.println("|CWD: " + CWD);
			System.out.println("+----------------------------+");

			cleanDeployDir();

			compiler.compile();

		}
	}

	private static void compileSingleFile(CompilerFacade compiler, String[] args) throws IOException {
		assert args[0] == "--single";
		assert args.length == 2;

		File fileToCompile = new File(args[1]);

		System.out.println("+-----------------------+");
		System.out.println("| compiling single file |");
		System.out.println("+-----------------------+");

		compiler.compile(fileToCompile);

		System.exit(0);
	}

	/**
	 * 1. Explicitly delete all .html files on the root level
	 * 2. Recursively delete all folders on the root level that doesn't have a specific name
	 */
	public static void cleanDeployDir() throws IOException {
		File deployDir = new File("deployment");

		for (File file : listFilesAndDirsFrom(deployDir, NONRECURSIVE)) {

			System.out.println(file);

			if (file.isFile())
					file.delete();
			else
				if (!file.getName().equals("resources"))
					deleteDirectory(file);

		}
	}

}
