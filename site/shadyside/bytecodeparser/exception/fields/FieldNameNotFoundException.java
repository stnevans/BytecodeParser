package site.shadyside.bytecodeparser.exception.fields;

import site.shadyside.bytecodeparser.exception.MemberNotContainedException;

public class FieldNameNotFoundException extends FieldParsingException {

	public FieldNameNotFoundException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2070988193307191003L;

}
