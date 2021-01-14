package common.html;

import java.io.File;
import java.io.IOException;

public interface HtmlTemplateStrategy {

	/**
	 * Insert the values supplied in the constructor into the template and return the values.
	 * @return complete html document formatted as a {@code String}
	 */
	String buildUsing(TemplateParameters parameters) throws Exception;

}
