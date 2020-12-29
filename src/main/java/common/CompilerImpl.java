package common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import common.html.tags.DefaultBodyTag;
import common.html.tags.HtmlTag;
import framework.Compiler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompilerImpl implements Compiler {

	private static final Compiler instance = new CompilerImpl();
	private CompilerImpl() {}

	@NotNull
	public static Compiler getInstance() {
		return instance;
	}

	@Override
	public String compile(String markdown)  {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        return html;
	}

	@Override
	public Map<File, String> compileAllFiles(Map<File, String> fileToMd) {
		Map<File, String> fileToHtml = new HashMap<>();

		Set<File> files = fileToMd.keySet();
		for (File file : files) {

			String  md      = fileToMd.get(file);
			String  html    = compile(md);
			HtmlTag htmlTag = buildDefaultHtmlTag(html);

			fileToHtml.put(file, html);
		}

		return fileToHtml;
	}

	private HtmlTag buildDefaultHtmlTag(String html) {
		return new DefaultBodyTag(html).Builder
	}

}
