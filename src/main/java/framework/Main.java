package framework;

import common.Culprit;
import common.culpritFactory.DefaultCulpritFactory;
import framework.other.CLIParser;
import framework.other.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static framework.Constants.Constants.CWD;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listFilesAndDirsFrom;
import static org.apache.commons.io.FileUtils.deleteDirectory;



public class Main {

	private static final Map<String, Boolean> argToVal = new HashMap<>();
	private static CLIParser parser;

	public static void main(String[] args) throws Exception {
		parser = new CLIParser();

		parser.parse(args);

		Logger.log(args, parser);

		Culprit culpritCompiler = new Culprit(new DefaultCulpritFactory());

		if (parser.getBoolValOf("--single"))
			compileSingleFile(culpritCompiler, args);
		else
			compileAllFiles(culpritCompiler);
	}

	private static void compileAllFiles(Culprit culpritCompiler) throws Exception {

		System.out.println();
		System.out.println(" > Compiling MULTIPLE Files");
		System.out.println();

		cleanDeployDir();

		culpritCompiler.compileAllFiles();
	}

	private static void compileSingleFile(Culprit compiler, String[] args) throws Exception {
		File fileToCompile = new File(parser.getStringValOf("file"));

		System.out.println();
		System.out.println(" > Compiling SINGLE File");
		System.out.println();

		compiler.
				compile(new File[]{
				fileToCompile
		});

		System.exit(0);
	}

	/**
	 * 1. Explicitly delete all .html files on the root level
	 * 2. Recursively delete all folders on the root level that doesn't have a specific name
	 */
	public static void cleanDeployDir() throws IOException {
		File deployDir = new File("deployment");

		for (File file : listFilesAndDirsFrom(deployDir, NONRECURSIVE)) {

			if (file.isFile())
					file.delete();
			else
				if (!file.getName().equals("resources"))
					deleteDirectory(file);

		}
	}

}
