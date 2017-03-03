package site.shadyside.bytecodeparser.parse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import site.shadyside.bytecodeparser.parse.classParser.ClassParser;
import site.shadyside.bytecodeparser.utils.IOUtils;

public class ByteCodeParser {
	
	public static ClassParser initParsing(Path path) throws IOException {
		ClassParser parser =  new ClassParser(path);
		System.out.println(parser.getConstantPool());
		return parser;
	}
	
	public static void parseJar(Path path) throws IOException{
		ZipFile file = new ZipFile(path.toFile());
		Enumeration<? extends ZipEntry> zipEntries = file.entries();
		List<ClassParser> classParsers = new ArrayList<ClassParser>();
		while(zipEntries.hasMoreElements()){
			ZipEntry entry = zipEntries.nextElement();
			byte[] bytes = IOUtils.getByteArray(file.getInputStream(entry));
			classParsers.add(new ClassParser(bytes,path));
		}
		
		file.close();
	}
	
}
