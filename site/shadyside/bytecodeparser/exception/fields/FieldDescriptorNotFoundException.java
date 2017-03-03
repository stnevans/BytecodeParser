package site.shadyside.bytecodeparser.exception.fields;

import site.shadyside.bytecodeparser.exception.MemberNotContainedException;


public class FieldDescriptorNotFoundException extends FieldParsingException {

	public FieldDescriptorNotFoundException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7034437660154459156L;
}
