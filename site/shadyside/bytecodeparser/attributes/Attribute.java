package site.shadyside.bytecodeparser.attributes;

import java.nio.ByteBuffer;

import site.shadyside.bytecodeparser.constantpool.ConstantPool;
import site.shadyside.bytecodeparser.constantpool.ConstantPoolEntry;
import site.shadyside.bytecodeparser.exception.MemberNotContainedException;
import site.shadyside.bytecodeparser.utils.ByteUtils;

public class Attribute {

	public enum AttributeType{
		ConstantValue,Code,StackMapTable,Exceptions,InnerClasses,EnclosingMethod,Synthetic,Signature,SourceFile,
		SourceDebugExtension,LineNumberTable,LocalVariableTable,LocalVariableTypeTable,Deprecated,RuntimeVisibleAnnotations,
		RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations,
		AnnotationDefault,BootstrapMethods;
	}

	public AttributeType getType(String name){
		if(name.equals("RuntimeVisibleParameterAnnotations")){
			return AttributeType.RuntimeVisibleParameterAnnotations;
		}
		if(name.equals("RuntimeInvisibleParameterAnnotations")){
			return AttributeType.RuntimeInvisibleParameterAnnotations;
		}
		if(name.equals("AnnotationDefault")){
			return AttributeType.AnnotationDefault;
		}
		if(name.equals("BootstrapMethods")){
			return AttributeType.BootstrapMethods;
		}
		if(name.equals("ConstantValue")){
			return AttributeType.ConstantValue;
		}
		if(name.equals("Code")){
			return AttributeType.Code;
		}
		if(name.equals("StackMapTable")){
			return AttributeType.StackMapTable;
		}
		if(name.equals("Exceptions")){
			return AttributeType.Exceptions;
		}
		if(name.equals("InnerClasses")){
			return AttributeType.InnerClasses;
		}
		if(name.equals("EnclosingMethod")){
			return AttributeType.EnclosingMethod;
		}
		if(name.equals("Synthetic")){
			return AttributeType.Synthetic;
		}
		if(name.equals("Signature")){
			return AttributeType.Signature;
		}
		if(name.equals("SourceFile")){
			return AttributeType.SourceFile;
		}
		if(name.equals("SourceDebugExtension")){
			return AttributeType.SourceDebugExtension;
		}
		if(name.equals("LineNumberTable")){
			return AttributeType.LineNumberTable;
		}
		if(name.equals("LocalVariableTable")){
			return AttributeType.LocalVariableTable;
		}
		if(name.equals("LocalVariableTypeTable")){
			return AttributeType.LocalVariableTypeTable;
		}
		if(name.equals("Deprecated")){
			return AttributeType.Deprecated;
		}
		if(name.equals("RuntimeVisibleAnnotations")){
			return AttributeType.RuntimeVisibleAnnotations;
		}
		if(name.equals("RuntimeInvisibleAnnotations")){
			return AttributeType.RuntimeInvisibleAnnotations;
		}
		return null;
	}
	private String name;
	private byte[] classData;
	private int readingIndex;
	private AttributeType type;
	private ConstantPool pool;

	public Attribute(String attributeName, byte[] classData, int readingIndex, ConstantPool pool){
		this.name = attributeName;
		this.classData = classData;
		this.readingIndex = readingIndex;
		this.type = getType(name);
		this.pool = pool;
	}
	public void parseAttribute() {
		switch(type){
		case AnnotationDefault:
			break;
		case BootstrapMethods:
			break;
		case Code:
			break;
		case ConstantValue:
			int valueIndex = readInt();
			break;
		case Deprecated:
			break;
		case EnclosingMethod:
			break;
		case Exceptions:
			int numExceptions = readInt();
			ConstantPoolEntry[] exceptions = new ConstantPoolEntry[numExceptions];
			for(int i = 0; i < numExceptions; i++){
				try {
					exceptions[i] = pool.getEntryAtIndex(readInt());
				} catch (MemberNotContainedException e) {
					e.printStackTrace();
				}
			}
			break;
		case InnerClasses:
			int numClasses = readInt();
			 
			for(int i = 0; i < numClasses; i++){
				int innerClassInfoIndex = readInt();
				int outerClassInfoIndex = readInt();
				int innerNameIndex = readInt();
				int flags = readInt();
				
			}
			break;
		case LineNumberTable:
			break;
		case LocalVariableTable:
			break;
		case LocalVariableTypeTable:
			break;
		case RuntimeInvisibleAnnotations:
			break;
		case RuntimeInvisibleParameterAnnotations:
			break;
		case RuntimeVisibleAnnotations:
			break;
		case RuntimeVisibleParameterAnnotations:
			break;
		case Signature:
			break;
		case SourceDebugExtension:
			break;
		case SourceFile:
			break;
		case StackMapTable:
			int numEntries = readInt();
			//TODO
			break;
		case Synthetic:
			break;
		default:
			break;

		}
	}

	/**
	 * Reads a two byte long int, wrapper for ByteUtil.getInt(Lbyte, I)
	 * @return
	 */
	private int readInt(){
		int ret = ByteUtils.getInt(classData, readingIndex);
		readingIndex+=2;
		return ret;
	}

	private int readFourByteInt(){
		byte[] intValue = new byte[4];
		for(int k = 0; k < 4; k++){
			intValue[k] = classData[readingIndex+k];
		}
		readingIndex+=4;
		return ByteBuffer.wrap(intValue).getInt();
		
	}
}
