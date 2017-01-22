package site.shadyside.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	private static Logger logger = new Logger();
	
	PrintWriter outputWriter;
	
	protected Logger(){
		try {
			outputWriter = new PrintWriter(new File("bytecodeLog.log"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String string){
		outputWriter.println(string);
		outputWriter.flush();
		System.out.println(string);
	}
	
	public static Logger getLogger(){
		return logger;
	}
	
}
