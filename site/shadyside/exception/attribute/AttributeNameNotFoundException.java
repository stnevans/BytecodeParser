package site.shadyside.exception.attribute;

import site.shadyside.exception.MemberNotContainedException;

public class AttributeNameNotFoundException extends AttributeParsingException{

	public AttributeNameNotFoundException(MemberNotContainedException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -667650955845977082L;

}
