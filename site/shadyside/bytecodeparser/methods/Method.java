package site.shadyside.bytecodeparser.methods;

import java.util.List;

import site.shadyside.bytecodeparser.attributes.AttributePool;
import site.shadyside.bytecodeparser.flags.MethodAccessFlag;

public class Method {

	public Method(List<MethodAccessFlag> flags, String methodName,
			String methodDescriptor, AttributePool attributePool) {
		this.flags = flags;
		this.name = methodName;
		this.descriptor = methodDescriptor;
		this.attributePool = attributePool;
	}
	private List<MethodAccessFlag> flags;
	private String name;
	private String descriptor;
	private AttributePool attributePool;
	public String toString(){
		return "Method " + name + " with flags " + flags.toString() + " descriptor " + descriptor + " and attributes " + attributePool.getAttributes();
	}
}
