package site.shadyside.parse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import site.shadyside.parse.classParser.ClassParser;

public class ByteCodeParser {
	
	public static void initParsing(Path path) throws IOException {
		new ClassParser(path);
	}
	
}
