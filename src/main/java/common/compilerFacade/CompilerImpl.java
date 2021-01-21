package common.compilerFacade;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import framework.compilerFacade.Compiler;
import org.jetbrains.annotations.NotNull;

public class CompilerImpl implements Compiler {

	private static final Compiler instance = new CompilerImpl();

	private static Parser parser;
	private static HtmlRenderer renderer;

	private CompilerImpl() {
        MutableDataSet options = new MutableDataSet();

        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        parser   = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
	}

	@NotNull
	public static Compiler getInstance() {
		return instance;
	}

	@Override
	public @NotNull String compile(String markdown)  {

        Node document = parser.parse(markdown);

        String html = renderer.render(document);

        return html;
	}

}
