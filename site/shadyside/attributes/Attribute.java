package site.shadyside.attributes;

import javax.swing.SortOrder;

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


	public Attribute(String attributeName, byte[] classData, int readingIndex){
		this.name = attributeName;
		this.classData = classData;
		this.readingIndex = readingIndex;
		this.type = getType(name);
	}
	public void parseAttribute() {
		switch(type){
		case AnnotationDefault:

		case BootstrapMethods:

		case Code:

		case ConstantValue:

		case Deprecated:

		case EnclosingMethod:

		case Exceptions:

		case InnerClasses:

		case LineNumberTable:

		case LocalVariableTable:

		case LocalVariableTypeTable:

		case RuntimeInvisibleAnnotations:

		case RuntimeInvisibleParameterAnnotations:

		case RuntimeVisibleAnnotations:

		case RuntimeVisibleParameterAnnotations:

		case Signature:

		case SourceDebugExtension:

		case SourceFile:
			
		case StackMapTable:
			break;
		case Synthetic:
			break;
		default:
			break;

		}
	}
}
