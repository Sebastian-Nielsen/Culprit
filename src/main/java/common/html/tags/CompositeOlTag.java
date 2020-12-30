package common.html.tags;

import framework.html.CompositeTag;
import framework.html.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeOlTag extends CompositeTag {

	protected final List<Tag> tags;

	@Override
	public String getHtml() {
		return tags.stream()
				.map(Tag::toString)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public CompositeOlTag(List<Tag> tags) {
		this.tags = tags;
	}

	public CompositeOlTag() {
		this.tags = new ArrayList<>();
	}


}
