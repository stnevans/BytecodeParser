package site.shadyside.bytecodeparser.fields;

import java.util.List;

import site.shadyside.bytecodeparser.attributes.AttributePool;
import site.shadyside.bytecodeparser.flags.FieldAccessFlag;

public class Field {
	public Field(List<FieldAccessFlag> flags, String fieldName,
			String fieldDescriptor, AttributePool attributePool) {
		this.accessFlags=flags;
		this.name = fieldName;
		this.descriptor = fieldDescriptor;
		this.attributePool = attributePool;
	}
	private List<FieldAccessFlag> accessFlags;
	private String name;
	private String descriptor;
	private AttributePool attributePool;
	
	public String toString(){
		return "Field " + name + " with flags " + accessFlags.toString() + " descriptor " + descriptor + " and attributes " + attributePool.getAttributes();
	}
}
