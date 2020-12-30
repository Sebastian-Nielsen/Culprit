package framework;

import common.compilerSettingsFactories.ProductionCompilerDependencyFactory;
import framework.singleClasses.CompilerFacade;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.cleanDirectory;

public class Main {

	public static void main(String[] args) throws Exception {

		CompilerFacade compiler =
			new CompilerFacade
				.Builder(new ProductionCompilerDependencyFactory())
				.setAddDefaultIndexes(true)
				.setAddIdToContentFilesWithoutOne(true)
				.build();

		cleanDeployDir();

		compiler.compile();

	}

	public static void cleanDeployDir() throws IOException {
		cleanDirectory(new File("deployment"));
	}

}
