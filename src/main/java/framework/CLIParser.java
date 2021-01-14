package framework;

import java.util.ArrayList;
import java.util.List;

public class CLIParser {

	private final List<Argument> parsedArguments = new ArrayList<>();

	/**
	 * Here are default values of args sat
	 */
	public CLIParser() {
		String[] defaultArgs = new String[]{"--no-single"};

		parse(defaultArgs);
	}

	public void parse(String[] args) {
		for (String arg : args) {

			if (arg.startsWith("--no"))
				addNegativeBool(arg);

			else if (arg.startsWith("--"))
				addPositiveBool(arg);

			else
				addKeyValArgument(arg); // of the form "<key>=<val>"

		}
	}

	private void addKeyValArgument(String arg) {
		String[] keyAndVal = arg.split("=");
		parsedArguments.add(
				new Argument(keyAndVal[0], keyAndVal[1])
		);
	}

	public boolean hasArgument(String keyToSearchFor) {
		return getArgumentBy(keyToSearchFor) != null;
	}

	public Argument getArgumentBy(String key) {
		for (Argument arg : parsedArguments)
			if (arg.key.equals(key))
				return arg;
		return null;
	}

	public String getStringValOf(String key) {
		return (String) getArgumentBy(key).val;
	}

	public boolean getBoolValOf(String key) {
		Argument arg = getArgumentBy(key);
		if (arg != null)
			return Boolean.parseBoolean(getArgumentBy(key).val);
		else
			return false;
	}

	private void addNegativeBool(String arg) {
		if (hasArgument(arg))
			getArgumentBy(arg).val = "false";
		else
			parsedArguments.add(new Argument(arg, "false"));
	}
	private void addPositiveBool(String arg) {
		if (hasArgument(arg))
			getArgumentBy(arg).val = "true";
		else
			parsedArguments.add(new Argument(arg, "true"));
	}


	private class Argument {
		public String key;
		public String val;

		public Argument(String key, String value) {
			this.key = key;
			this.val = value;
		}
	}

}
