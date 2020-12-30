package framework.html;

import java.util.List;

public abstract class CompositeTag extends Tag {

	protected List<Tag> tags;

	@Override
	public void addTag(Tag tag) {
		tags.add(tag);
	}

}
