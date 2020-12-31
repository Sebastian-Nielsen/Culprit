package framework;

import common.compilerSettingsFactories.ProductionCompilerDependencyFactory;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.*;

public class Main {

	public static void main(String[] args) throws Exception {

		CompilerFacade compiler =
			new CompilerFacade
				.Builder(new ProductionCompilerDependencyFactory())
				.setAddDefaultIndexes(true)
				.setAddIdToContentFilesWithoutOne(true)
				.build();

		cleanDeployDir();

//		compiler.compile();

	}

	/**
	 * 1. Explicitly delete all .html files on the root level
	 * 2. Recursively delete all folders on the root level that doesn't have a specific name
	 */
	public static void cleanDeployDir() throws IOException {
		File dir = new File("deployment");
		String[] extToList = new String[]{"html"};
		boolean recursive = false;

		// TODO make the fileUtils retriever methods "those that list stuff" more generic
		// TODO by having them take an enum argument: {RECURSIVE, NONRECURSIVE}
//		for (File file : listFiles(dir, extToList, recursive)) {
		for (File file : listFilesAndDirs(dir, null, null)) {

			System.out.println(file);

//			continue;
//			boolean hasHtmlFileExt = file.getName().endsWith(".html");
//			if (file.isFile() && hasHtmlFileExt)
//					file.delete();
//
//
//			if (file.isDirectory())
//				file.
		}

//		cleanDirectory(new File("deployment"));
	}

}
