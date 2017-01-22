package site.shadyside.exception;

import java.io.IOException;

public class InvalidFileFormatException extends IOException{

	public InvalidFileFormatException(String string) {
		super(string);
	}

	public InvalidFileFormatException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5004846744866969063L;

}
