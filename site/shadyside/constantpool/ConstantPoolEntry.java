package site.shadyside.constantpool;

import java.nio.ByteBuffer;
import java.util.Arrays;

import site.shadyside.exception.InvalidConstantPoolMemberException;
import site.shadyside.utils.ByteUtils;

public class ConstantPoolEntry {

	public enum Type{
		CLASS,FIELDREF,METHODREF,INTERFACEMETHODREF,STRING,INTEGER,FLOAT,LONG,DOUBLE,NAMEANDTYPE,UTF8,METHODHANDLE,METHODTYPE,INVOKEDYNAMIC;
	}
	public static Type getType(int tag) throws InvalidConstantPoolMemberException{
		if(tag == 7){
			return Type.CLASS;
		}else if(tag == 9){
			return Type.FIELDREF;
		}else if(tag == 10){
			return Type.METHODREF;
		}else if(tag == 11){
			return Type.INTERFACEMETHODREF;
		}else if(tag == 8){
			return Type.STRING;
		}else if(tag == 3){
			return Type.INTEGER;
		}else if(tag == 4){
			return Type.FLOAT;
		}else if(tag == 5){
			return Type.LONG;
		}else if(tag == 6){
			return Type.DOUBLE;
		}else if(tag == 12){
			return Type.NAMEANDTYPE;
		}else if(tag == 1){
			return Type.UTF8;
		}else if(tag == 15){
			return Type.METHODHANDLE;
		}else if(tag == 16){
			return Type.METHODTYPE;
		}else if(tag == 18){
			return Type.INVOKEDYNAMIC;
		}
		throw new InvalidConstantPoolMemberException();
	}

	public ConstantPoolEntry(int tag, byte[] classData, int readingIndex) throws InvalidConstantPoolMemberException{
		entryType = getType(tag);
		this.classData = classData;
		this.readingIndex = readingIndex;
		size = 0;
	}

	private Type entryType;
	private int size;
	private byte[] classData;
	private int readingIndex;
	private int index;
	private Object value;
	public int getSize(){
		return size;
	}

	public void parseData() {

		//TODO make a switch
		if(this.entryType == Type.UTF8){
			int size = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;

			String UTF8String = new String(Arrays.copyOfRange(classData, readingIndex, readingIndex+size));
			this.size += size;
			this.value = UTF8String;
		}
		if(this.entryType == Type.CLASS){
			int index = ByteUtils.getInt(classData, readingIndex);
			this.size += 2;
			this.value = index;
		}
		if(this.entryType == Type.NAMEANDTYPE){
			int nameIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			int descriptorIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			this.value = new int[]{nameIndex,descriptorIndex};
		}
		if(this.entryType == Type.METHODREF){
			int classIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			int nameAndTypeIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			this.value = new int[]{classIndex,nameAndTypeIndex};
		}
		if(this.entryType == Type.FIELDREF){
			int classIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			int nameAndTypeIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			this.value = new int[]{classIndex,nameAndTypeIndex};
		}
		if(this.entryType == Type.INTERFACEMETHODREF){
			int classIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			int nameAndTypeIndex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			this.size += 2;
			this.value = new int[]{classIndex,nameAndTypeIndex};
		}
		if(this.entryType == Type.DOUBLE){
			byte[] doubleValue = new byte[8];
			for(int i = 0; i < 8; i++){
				doubleValue[i] = classData[readingIndex+i];
			}
			double value = ByteBuffer.wrap(doubleValue).getDouble();
			this.size += 8;
			this.value = value;
		}
		if(this.entryType == Type.LONG){
			long value = ByteUtils.getLong(classData,readingIndex);
			this.size+=8;
			this.value = value;
		}
		if(this.entryType == Type.METHODHANDLE){
			int referenceKind = classData[readingIndex];
			readingIndex++;
			size++;
			int referenceIndex = ByteUtils.getInt(classData, readingIndex);
			size+=2;
			this.value = new int[]{referenceKind,referenceIndex};
		}
		if(this.entryType == Type.METHODTYPE){
			int descriptorIndex = ByteUtils.getInt(classData, readingIndex);
			size+=2;
			this.value = descriptorIndex;
		}
		if(this.entryType == Type.INVOKEDYNAMIC){
			int bootstrapmethodattrindex = ByteUtils.getInt(classData, readingIndex);
			readingIndex+=2;
			size+=2;
			int nameAndTypeInex = ByteUtils.getInt(classData, readingIndex);
			size+=2;
			this.value = new int[]{bootstrapmethodattrindex,nameAndTypeInex};
		}
		if(this.entryType == Type.INTEGER){
			byte[] intValue = new byte[4];
			for(int i = 0; i < 4; i++){
				intValue[i] = classData[readingIndex+i];
			}
			int value = ByteBuffer.wrap(intValue).getInt();
			this.size += 4;
			this.value = value;
		}
		if(this.entryType == Type.FLOAT){
			byte[] floatValue = new byte[4];
			for(int i = 0; i < 4; i++){
				floatValue[i] = classData[readingIndex+i];
			}
			float value = ByteBuffer.wrap(floatValue).getFloat();
			this.size += 4;
			this.value = value;
		}
	}

	public Type getEntryType(){
		return this.entryType;
	}

	public void setIndex(int i){
		this.index = i;
	}

	public int getIndex(){
		return this.index;
	}
	
	@Override
	public String toString(){
		return entryType.toString();
	}
	
	public Object getValue(){
		return value;
	}
}
