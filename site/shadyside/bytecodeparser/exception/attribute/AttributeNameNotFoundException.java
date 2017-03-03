package site.shadyside.bytecodeparser.exception.attribute;

import site.shadyside.bytecodeparser.exception.MemberNotContainedException;

public class AttributeNameNotFoundException extends AttributeParsingException{

	public AttributeNameNotFoundException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -667650955845977082L;

}
