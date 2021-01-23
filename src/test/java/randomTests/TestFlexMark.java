package randomTests;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static framework.utils.FileUtils.Retriever.contentOf;
import static org.apache.commons.io.FileUtils.readFileToString;

public class TestFlexMark {

	@Test
	public void test() {

		File B = new File("C:\\Users\\sebas\\IdeaProjects\\culprit_2\\content\\other\\fileB.md");

//		String md = contentOf(B);

		String md = """
				[ID]:<> (24c4907d-332f-4f55-86f8-4e1cfc772b94)
				    
								
				This is a "test", that is <<what he said>> -- haha, I know. I need to stop.
				    
				    asdf
				""";

		String result = compile(md);

		System.out.println(result);


	}


	public String compile(String markdown)  {

		 final DataHolder OPTIONS = new MutableDataSet()
		        .set(Parser.EXTENSIONS, Arrays.asList(AbbreviationExtension.create(),
				                                FootnoteExtension.create(),
				        TypographicExtension.create()))
		        .toImmutable();

//        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        return html;
	}

}
