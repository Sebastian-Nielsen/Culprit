package common;

import common.culpritFactory.DefaultPostEffectFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static framework.utils.FileUtils.Modifier.writeStringTo;

public class PostEffectFacade {


	private final boolean shouldPrettifyHtml;

	public PostEffectFacade(DefaultPostEffectFactory factory) {
		this.shouldPrettifyHtml = factory.shouldPrettifyHtml();
	}

	public void effectsFor(Map<File, String> contentFileToHtml) {

		Set<File> files = contentFileToHtml.keySet();
		for (File contentFile : files) {

			String html = contentFileToHtml.get(contentFile);
			effectsFor(contentFile, html);
		}

		if (shouldPrettifyHtml)
			prettifyHtml(contentFileToHtml);

	}

	public void effectsFor(File contentFile, String html) {
//		File deployFile = deployer.getDeployEquivalentOf(contentFile);

//		writeStringTo(deployFile, html);
	}

	/* === PRIVATE METHODS */

	private void prettifyHtml(Map<File, String> fileToHtml) {
		for (File file : fileToHtml.keySet()) {
			Document doc = Jsoup.parse(fileToHtml.get(file), "", Parser.xmlParser());
			fileToHtml.put(file, doc.toString());
		}
	}
}
