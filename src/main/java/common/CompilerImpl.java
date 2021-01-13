package common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import common.html.ArticleTag;
import common.html.HtmlTemplateStrategy;
import common.html.concreteHtmlTemplates.DefaultPageHtmlTemplate;
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
	public ArticleTag compile(String markdown)  {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        return new ArticleTag(html);
	}

	@Override
	public Map<File, String> compileAllFiles(Map<File, String> fileToMd) throws Exception {
		Map<File, String> fileToHtml = new HashMap<>();

		Set<File> files = fileToMd.keySet();
		for (File file : files) {

			String     md   = fileToMd.get(file);
			ArticleTag tag  = compile(md);
			String     html = tag.insertInto(new DefaultPageHtmlTemplate());

			fileToHtml.put(file, html);
		}

		return fileToHtml;
	}

	private String compileAndInsertInto(String markdown, HtmlTemplateStrategy htmlTemplate) throws Exception {
		ArticleTag tag = compile(markdown);
		return     tag.insertInto(htmlTemplate);
	}

}
