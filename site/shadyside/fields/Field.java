package site.shadyside.fields;

import java.util.List;

import site.shadyside.attr.Attribute;
import site.shadyside.flags.FieldAccessFlag;

public class Field {
	public Field(List<FieldAccessFlag> flags, String fieldName,
			String fieldDescriptor, List<Attribute> attributes) {
		this.accessFlags=flags;
		this.name = fieldName;
		this.descriptor = fieldDescriptor;
		this.attributes = attributes;
	}
	private List<FieldAccessFlag> accessFlags;
	private String name;
	private String descriptor;
	private List<Attribute> attributes;
	
	public String toString(){
		return "Field " + name + " with flags " + accessFlags.toString() + " descriptor " + descriptor + " and attributes " + attributes;
	}
}
