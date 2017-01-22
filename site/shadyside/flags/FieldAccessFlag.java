package site.shadyside.flags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FieldAccessFlag {
	ACC_PUBLIC(0x0001),ACC_PRIVATE(0x0002),ACC_PROTECTED(0x0004),ACC_STATIC(0x0008),
	ACC_FINAL(0x0010),ACC_VOLATILE(0x0040),ACC_TRANSIENT(0x0080),ACC_SYNTHETIC(0x1000),
	ACC_ENUM(0x4000);
	private int value;
	static List<FieldAccessFlag> flags = new ArrayList<FieldAccessFlag>(Arrays.asList(ACC_ENUM,ACC_FINAL,ACC_PRIVATE,ACC_PROTECTED,ACC_PUBLIC,ACC_STATIC,ACC_SYNTHETIC,ACC_TRANSIENT,ACC_VOLATILE));
	private FieldAccessFlag(int val){
		this.value = val;
	}
	
	public static List<FieldAccessFlag> getFlags(int accessValue){
		ArrayList<FieldAccessFlag> includedFlags = new ArrayList<FieldAccessFlag>();
		for(FieldAccessFlag flag : flags){
			if((flag.value & accessValue) > 0){
				includedFlags.add(flag);
			}
		}
		return includedFlags;
	}
	
	
}
