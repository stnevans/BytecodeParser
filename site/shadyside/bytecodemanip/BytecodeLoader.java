package site.shadyside.bytecodemanip;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import site.shadyside.commandline.OptionParser;
import site.shadyside.exception.InvalidFileTypeException;
import site.shadyside.parse.ByteCodeParser;

public class BytecodeLoader {
	public static void main(String[] args) throws InvalidFileTypeException{
		OptionParser parser = new OptionParser(args);
		Path path = Paths.get(parser.getSwitch("f", null));
		//To start, we assume the file is a class file.
		if(!path.toString().endsWith(".class")){
			throw new InvalidFileTypeException();
		}
		try {
			ByteCodeParser.initParsing(path);
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}
	}
}
