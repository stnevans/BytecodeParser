package site.shadyside.bytecodeparser.flags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MethodAccessFlag {
	ACC_PUBLIC(0x0001),ACC_PRIVATE(0x0002),ACC_PROTECTED(0x0004),ACC_STATIC(0x0008),ACC_FINAL(0x0010),
	ACC_SYNCHRONIZED(0x0020),ACC_BRIDGE(0x0040),ACC_VARARGS(0x0080),ACC_NATIVE(0x0100),ACC_ABSTRACT(0x0400),
	ACC_STRICT(0x0800),ACC_SYNTHETIC(0x1000);
	private int value;
	static List<MethodAccessFlag> flags = new ArrayList<MethodAccessFlag>(Arrays.asList(ACC_ABSTRACT,ACC_BRIDGE,ACC_FINAL,ACC_NATIVE,ACC_PRIVATE,ACC_PROTECTED,ACC_PUBLIC,ACC_STATIC,ACC_STRICT,ACC_SYNCHRONIZED,ACC_SYNTHETIC,ACC_VARARGS));
	private MethodAccessFlag(int val){
		this.value = val;
	}
	
	
	public static List<MethodAccessFlag> getFlags(int accessValue){
		ArrayList<MethodAccessFlag> includedFlags = new ArrayList<MethodAccessFlag>();
		for(MethodAccessFlag flag : flags){
			if((flag.value & accessValue) > 0){
				includedFlags.add(flag);
			}
		}
		return includedFlags;
	}
}
