package site.shadyside.bytecodeparser.exception.methods;

public class MethodParsingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2457519914849624543L;
	
	public MethodParsingException(Exception e){
		super(e);
	}
}
