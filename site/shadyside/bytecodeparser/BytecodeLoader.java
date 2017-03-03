package site.shadyside.bytecodeparser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import site.shadyside.bytecodeparser.commandline.OptionParser;
import site.shadyside.bytecodeparser.exception.InvalidFileTypeException;
import site.shadyside.bytecodeparser.parse.ByteCodeParser;

public class BytecodeLoader {
	public static void main(String[] args) throws InvalidFileTypeException{
		OptionParser parser = new OptionParser(args);
		Path path = Paths.get(parser.getSwitch("f", null));

		//To start, we assume the file is a class file.
		
		if(path.toString().endsWith(".class")){
			try {
				ByteCodeParser.initParsing(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(path.toString().endsWith(".jar")){
			try {
				ByteCodeParser.parseJar(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new InvalidFileTypeException();
		}
	}
}
