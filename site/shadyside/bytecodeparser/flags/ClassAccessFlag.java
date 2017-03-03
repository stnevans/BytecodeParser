package site.shadyside.bytecodeparser.flags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum ClassAccessFlag{
	ACC_PUBLIC(0x0001),ACC_FINAL(0x0010),
	ACC_SUPER(0x0020),ACC_INTERFACE(0x0200),ACC_ABSTRACT(0x0400),
	ACC_SYNTHETIC(0x1000),ACC_ANNOTATION(0x2000),ACC_ENUM(0x4000);
	int value;
	static List<ClassAccessFlag> flags = new ArrayList<ClassAccessFlag>(Arrays.asList(ACC_PUBLIC,ACC_ABSTRACT,ACC_ANNOTATION,ACC_ENUM,ACC_FINAL,ACC_INTERFACE,ACC_SUPER,ACC_SYNTHETIC));
	
	private ClassAccessFlag(int flagValue){

		this.value = flagValue;
	}
	public static List<ClassAccessFlag> getFlags(int accessValue){
		ArrayList<ClassAccessFlag> includedFlags = new ArrayList<ClassAccessFlag>();
		for(ClassAccessFlag flag : flags){
			if((flag.value & accessValue) > 0){
				includedFlags.add(flag);
			}
		}
		return includedFlags;
	}
}
