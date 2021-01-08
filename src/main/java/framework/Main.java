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
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.deleteDirectory;


public class Main {

	private static final Map<String, Boolean> argToVal = new HashMap<>();

	private static void handleArgs(String[] args) {
		assert args.length % 2 == 0;

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
		System.out.println("length of args " + args.length);
		handleArgs(args);

		System.out.println("----------");
		for (String arg : argToVal.keySet()) {
			System.out.println(arg);
			System.out.println(argToVal.get(arg));
			System.out.println();
		}

		boolean shouldPrettifyHtml = argToVal.get("--prettifyHtml");
		File singleFileToCompile   = argToVal.get("--single");

		CompilerFacade compiler =
			new CompilerFacade
				.Builder(new ProductionCompilerDependencyFactory())
				.setAddDefaultIndexes(true)
				.setAddIdToContentFilesWithoutOne(true)
				.setPrettifyHtml(shouldPrettifyHtml)
				.setCompileSingleFile()
				.build();

		System.out.println(new File("").getAbsoluteFile().getName());
		System.out.println(System.getProperty("user.dir"));

//		cleanDeployDir();

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
