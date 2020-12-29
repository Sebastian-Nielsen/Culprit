package framework;

import common.compilerSettingsFactories.CustomCompilerDependencyFactory;
import common.compilerSettingsFactories.ProductionCompilerDependencyFactory;
import framework.singleClasses.CompilerFacade;
import org.apache.commons.io.FileUtils;

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

		compiler.compile();
//		cleanDeploy();

	}

	public static void cleanDeploy() throws IOException {
		cleanDirectory(new File("deployment"));
	}

}
