package common.html.tags;

import framework.html.BodyTag;
import framework.html.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeBodyTag extends Tag implements BodyTag {

	private final List<Tag> tags;

	public CompositeBodyTag(List<Tag> tags) {
		this.tags = tags;
	}

	public CompositeBodyTag() {
		this.tags = new ArrayList<>();
	}

	public String toString() {
		return "<body>\n\n" +
				getTags() + '\n' +
				"</body>\n";
	}

	public String getTags() {
		return tags.stream()
				.map(Tag::toString)
				.collect(Collectors.joining(""));
	}

}
