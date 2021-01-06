package framework;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
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

	public static void main(String[] args) throws Exception {

		CompilerFacade compiler =
			new CompilerFacade
				.Builder(new ProductionCompilerDependencyFactory())
				.setAddDefaultIndexes(true)
				.setAddIdToContentFilesWithoutOne(true)
				.setPrettifyHtml(true)
				.build();

		System.out.println(new File("").getAbsoluteFile().getName());
		System.out.println(System.getProperty("user.dir"));

		cleanDeployDir();

		compiler.compile();

	}

	public static String compile(String markdown)  {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        return html;
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

//		cleanDirectory(new File("deployment"));
	}

}
