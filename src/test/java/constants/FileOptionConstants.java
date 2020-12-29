package constants;

import static common.fileOption.FileOption.KEY.*;
import static common.fileOption.FileOption.*;

public class FileOptionConstants {
	public static final String FILEOPTION_TEMPLATE = "[%s]: <> (%s)";

	public static final KEY    ARBITRARY_KEY_1 = ID;
	public static final String ARBITRARY_VAL_1 = "5fdc6765-6622-4e00-80e1-91b5be6fe0e1";
	public static final String ARBITRARY_FILEOPTION_1 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_1, ARBITRARY_VAL_1);

	public static final KEY    ARBITRARY_KEY_2 = D_LINKS;
	public static final String ARBITRARY_VAL_2 = "false";
	public static final String ARBITRARY_FILEOPTION_2 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_2, ARBITRARY_VAL_2);

	public static final KEY    ARBITRARY_KEY_3 = ID;
	public static final String ARBITRARY_VAL_3 = "7084d2f4-3c1a-4174-9736-9804b4857de5";
	public static final String ARBITRARY_FILEOPTION_3 = FILEOPTION_TEMPLATE.formatted(ARBITRARY_KEY_3, ARBITRARY_VAL_3);


}
