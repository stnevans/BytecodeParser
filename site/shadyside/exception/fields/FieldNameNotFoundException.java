package site.shadyside.exception.fields;

import site.shadyside.exception.MemberNotContainedException;

public class FieldNameNotFoundException extends FieldParsingException {

	public FieldNameNotFoundException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2070988193307191003L;

}
