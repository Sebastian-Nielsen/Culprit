package framework.Constants;

import java.io.File;

public class Constants {

	// E.g.     'C:\Users\sebas\IdeaProjects\culprit_2'
	public final static String CWD = System.getProperty("user.dir");

	// E.g.     'culprit_2'
	public final static String CWD_NAME = new File("").getAbsoluteFile().getName();

}
