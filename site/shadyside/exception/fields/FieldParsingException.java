package site.shadyside.exception.fields;

import site.shadyside.exception.MemberNotContainedException;

public class FieldParsingException extends Exception{

	public FieldParsingException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3684649597187074175L;

}
