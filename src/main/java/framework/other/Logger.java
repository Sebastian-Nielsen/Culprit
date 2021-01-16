package framework.other;

import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static framework.Constants.Constants.CWD;

public class Logger {

	private static final boolean LOG_UNPARSED_ARGUMENTS = false;
	private static final boolean LOG_PARSED_ARGUMENTS   = true;

	private static final boolean LOG_COMPILER_DATA_CONTAINER = true;

	private static final boolean LOG_SYSTEM_INFO = false;

	private static final boolean LOG_FILE_TO_COMPILE = true;

	public static void log(String[] args, CLIParser parser) {
		logSystemInfo();
		log(args);
		log(parser);
	}

	public static void log(File[] filesToCompile) {
		if (!LOG_FILE_TO_COMPILE) return;

		System.out.println();
		System.out.println("+-[CULPRIT]-Files-to-compile------[START]-+");
		System.out.println("|");
		int i = 1;
		for (File file : filesToCompile) {
			System.out.println("| " + i + ") '" + file + "'");
			i++;
		}
		System.out.println("|");
		System.out.println("+-[CULPRIT]-Files-to-compile------[END]---+");
		System.out.println();
	}

	public static void logSystemInfo() {
		if (!LOG_SYSTEM_INFO) return;

		System.out.println();
		System.out.println("+-[MAIN]----System-info-----------[START]-+");
		System.out.println("|");
		System.out.println("| CWD: " + CWD);
		System.out.println("|");
		System.out.println("+-[MAIN]----System-info-----------[END]---+");
		System.out.println();
	}

	public static void log(String[] args) {
		if (!LOG_UNPARSED_ARGUMENTS) return;

		System.out.println();
		System.out.println("+-[MAIN]----CLI-Arguments---------[START]-+");
		System.out.println("|");
		int i = 1;
		for (String arg : args) {
			System.out.println("| " + i + ") '" + arg + "'");
			i++;
		}
		System.out.println("|");
		System.out.println("+-[MAIN]----CLI-Arguments---------[END]---+");
		System.out.println();
	}

	public static void log(CLIParser parser) {
		 if (!LOG_PARSED_ARGUMENTS) return;

		System.out.println();
		System.out.println("+-[MAIN]----CLI-ParsedArguments---[START]-+");
		System.out.println("|");
		int i = 1;
		for (CLIParser.Argument arg : parser.getArguments()) {
			System.out.println("| " + i + ") '" + arg.key + "' = '" + arg.val + "'");
			i++;
		}
		System.out.println("|");
		System.out.println("+-[MAIN]----CLI-ParsedArguments---[END]---+");
		System.out.println();
	}


	public static void log(String file, FileOptionContainer foContainer) {
		System.out.println("+--------------------------------------------+");
		System.out.println("| File: '" + file + "'");
		System.out.println("| foContainer: ");
		for (Map.Entry<FileOption.KEY, String> entry : foContainer.entrySet())
			System.out.println("| '" + entry.getKey() + "' = '" + entry.getValue() + "'");
		System.out.println("+--------------------------------------------+");
	}
	public static void log(Map<String, FileOptionContainer> pathToFOContainer) {
		if (!LOG_COMPILER_DATA_CONTAINER) return;

		System.out.println();
		System.out.println("+-[DataExtractor]-CompilerDataContainer-[START]-+");
		System.out.println("|");
		System.out.println("| file to foContainer:");
		System.out.println("| ===================");
		for (Map.Entry<String, FileOptionContainer> entry : pathToFOContainer.entrySet()) {
			System.out.println("| File: '" + Path.of(CWD).relativize(Path.of(entry.getKey())) + "'");
			for (Map.Entry<FileOption.KEY, String> e : entry.getValue().entrySet()) {
				System.out.println("| - '" + e.getKey() + "': '" + e.getValue() + "'");
			}
			System.out.println("| ===================");
		}
		System.out.println("|");
		System.out.println("+-[DataExtractor]-CompilerDataContainer-[END]---+");
		System.out.println();
	}


}
