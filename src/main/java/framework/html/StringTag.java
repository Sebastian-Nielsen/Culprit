package framework.html;

import java.util.List;

public abstract class StringTag extends Tag {

	protected String content;

	@Override
	public void addTag(Tag tag) {
		content += tag.toString() + '\n';
	}
}
