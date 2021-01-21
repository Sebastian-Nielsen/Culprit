package randomTests;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import java.io.File;

import static framework.utils.FileUtils.Retriever.contentOf;
import static org.apache.commons.io.FileUtils.readFileToString;

public class TestFlexMark {

	@Test
	public void test() {

		File B = new File("C:\\Users\\sebas\\IdeaProjects\\culprit_2\\content\\other\\fileB.md");

		String md = contentOf(B);

//		String md = """
//				line 1
//
//				line 2
//
//				line 3
//				""";

		String result = compile(md);

		System.out.println(result);


	}


	public String compile(String markdown)  {
        MutableDataSet options = new MutableDataSet();

//        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);

        return html;
	}

}
