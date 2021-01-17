package framework.Constants;

import java.io.File;

public class Constants {

	// E.g.     'C:\Users\sebas\IdeaProjects\culprit_2'
	public static final String CWD = System.getProperty("user.dir");

	// E.g.     'culprit_2'
	public static final String CWD_NAME = new File("").getAbsoluteFile().getName();

	// E.g.     new File('culprit_2')
	public static final File ROOT_FILE = new File(CWD);

}
