package common.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static common.html.HTML.Attribute;

public class Tag {

	public enum TYPE {
		BODY, HEAD, DIV, A, LI, OL, HTML, LINK, ARTICLE, ASIDE, META;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	private final TYPE type;
	private final Map<Attribute, String> attrToVal;
	private final List<Tag> childrenTags = new ArrayList<>();
	private String content = "";

	public Tag(TYPE type) {
		this.type = type;
		this.attrToVal = new HashMap<>();
	}

	public Tag(TYPE type, Map<HTML.Attribute, String> attrToVal) {
		this.type = type;
		this.attrToVal = new HashMap<>(attrToVal);
	}

	public String toString() {
		return getOpenTag() +
				getContent() +
				getChildrenTags() +
				getCloseTag();
	}

	private String getContent() {
		if (content.equals(""))
			return content;
		else
			return content + '\n';
	}

	private String getAttributes() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Attribute
				attr : attrToVal.keySet())

			stringBuilder
					.append(attr)
					.append("=\"")
					.append( attrToVal.get(attr) )
					.append("\" ");

		return "" + stringBuilder;
	}

	public String getChildrenTags() {
		return childrenTags.stream()
				.map(Tag::toString)
				.collect(Collectors.joining("\n"));
	}

	private String getOpenTag() {
		return "<" + type + " " + getAttributes() + ">\n";
	}

	private String getCloseTag() {
		return "</" + type + ">\n";
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void putAttrToVal(HTML.Attribute attr, String value) {
		attrToVal.put(attr, value);
	}

	public String getAttr(String value) {
		return attrToVal.get(value);
	}

	public void addChild(Tag tag) {
		childrenTags.add(tag);
	}

	public Tag getChild(int index) {
		return childrenTags.get(index);
	}

	public void addChildren(List<Tag> tags) {
		childrenTags.addAll(tags);
	}

}
