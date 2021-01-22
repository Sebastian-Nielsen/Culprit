package common.compilerFacade;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import framework.compilerFacade.Compiler;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CompilerImpl implements Compiler {

	private static final Compiler instance = new CompilerImpl();

	private static Parser parser;
	private static HtmlRenderer renderer;

	private CompilerImpl() {
        final DataHolder OPTIONS = new MutableDataSet()
		        .set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "↩")
		        .set(Parser.EXTENSIONS,
				        Arrays.asList(
				        		AbbreviationExtension.create(),
						        FootnoteExtension.create(),
						        AnchorLinkExtension.create()

				        )
		        )
		        .toImmutable();

//        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        parser   = Parser.builder(OPTIONS).build();
        renderer = HtmlRenderer.builder(OPTIONS).build();
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
