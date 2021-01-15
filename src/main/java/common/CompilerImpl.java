package common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import common.html.HtmlTemplateStrategy;
import common.html.TemplateParameters;
import common.html.concreteHtmlTemplates.DefaultPageHtmlTemplate;
import framework.Compiler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.html.concreteHtmlTemplates.Helper.buildDefaultPageHtmlTemplateUsing;

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

//	@Override
//	public Map<File, String> compileAllFiles(Map<File, String> fileToMd) throws Exception {
//
//		Map<File, String> contentFileToHtml = new HashMap<>();
//
//		Set<File> contentFiles = fileToMd.keySet();
//		for (File contentFile : contentFiles) {
//
//			String md         = fileToMd.get(contentFile);
//			String articleTag = compile(md);
//			String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag);
//
//			contentFileToHtml.put(contentFile, htmlTag);
//		}
//
//		return contentFileToHtml;
//	}



}
