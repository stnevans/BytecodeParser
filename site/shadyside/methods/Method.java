package site.shadyside.methods;

import java.util.List;

import site.shadyside.attr.Attribute;
import site.shadyside.flags.MethodAccessFlag;

public class Method {

	public Method(List<MethodAccessFlag> flags, String methodName,
			String methodDescriptor, List<Attribute> attributes) {
		this.flags = flags;
		this.name = methodName;
		this.descriptor = methodDescriptor;
		this.attributes = attributes;
	}
	private List<MethodAccessFlag> flags;
	private String name;
	private String descriptor;
	private List<Attribute> attributes;
	public String toString(){
		return "Method " + name + " with flags " + flags.toString() + " descriptor " + descriptor + " and attributes " + attributes;
	}
}
