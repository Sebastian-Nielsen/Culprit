package framework.html;

import com.google.gson.JsonObject;

import java.util.List;

public abstract class Tag {

	protected String tagType;



	public String toString() {
		return getStartTag() + '\n' +
				getHtml() + '\n' +
				getEndTag();
	}

	abstract public String getHtml();

	String getStartTag() {
		return "<" + tagType + ">";
	}

	String getEndTag() {
		return "</" + tagType + ">";
	}

	abstract void addTag(Tag tag);

}
