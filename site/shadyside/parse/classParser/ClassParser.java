package site.shadyside.parse.classParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import site.shadyside.attributes.Attribute;
import site.shadyside.attributes.AttributePool;
import site.shadyside.constantpool.ConstantPool;
import site.shadyside.constantpool.ConstantPoolEntry;
import site.shadyside.constantpool.ConstantPoolEntry.Type;
import site.shadyside.exception.InvalidConstantPoolMemberException;
import site.shadyside.exception.InvalidFileFormatException;
import site.shadyside.exception.MemberNotContainedException;
import site.shadyside.exception.attribute.AttributeNameNotFoundException;
import site.shadyside.exception.fields.FieldDescriptorNotFoundException;
import site.shadyside.exception.fields.FieldNameNotFoundException;
import site.shadyside.exception.fields.FieldParsingException;
import site.shadyside.exception.methods.MethodDescriptorNotFoundException;
import site.shadyside.exception.methods.MethodNameNotFoundException;
import site.shadyside.exception.methods.MethodParsingException;
import site.shadyside.fields.Field;
import site.shadyside.fields.FieldPool;
import site.shadyside.flags.ClassAccessFlag;
import site.shadyside.flags.FieldAccessFlag;
import site.shadyside.flags.MethodAccessFlag;
import site.shadyside.logger.Logger;
import site.shadyside.methods.Method;
import site.shadyside.methods.MethodPool;
import site.shadyside.utils.ByteUtils;

public class ClassParser {
	private byte[] classData;
	private int readingIndex;
	private Path pathToClassFile;
	
	private ConstantPool constantPool;
	private FieldPool fieldPool;
	private MethodPool methodPool;
	private AttributePool attributePool;
	
	
	public ClassParser(Path pathToClassFile) throws IOException{
		this.pathToClassFile = pathToClassFile;
		classData = Files.readAllBytes(pathToClassFile);
		parse();
	}

	public void parse() throws InvalidFileFormatException{
		Logger logger = Logger.getLogger();
		if(!(classData[0] == -54 && classData[1] == -2 && classData[2] == -70 && classData[3] == -66)){
			throw new InvalidFileFormatException("The magic bytes of " + pathToClassFile.toString() + " are incorrect.");
		}
		logger.log("Parsing class file " + pathToClassFile.toString());
		logger.log("The magic bytes are correct.");
		int poolSize = ByteUtils.getInt(classData,8); 
		logger.log("Constant Pool Count(size + 1): " + poolSize);
		readingIndex = 10;
		try {
			constantPool = readConstantPool(poolSize);
		} catch (InvalidConstantPoolMemberException e) {
			logger.log("[ERROR] : Invalid Class Pool Member Found");
			throw new InvalidFileFormatException(e);
		}
		logger.log("Constant Pool Parsing Complete");


		String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
		int intFl = Integer.parseInt(fls,2);
		readingIndex+=2;

		List<ClassAccessFlag> flags = ClassAccessFlag.getFlags(intFl);
		logger.log("Access flags: " + flags);

		int indexOfThisClass = ByteUtils.getInt(classData, readingIndex);
		readingIndex+=2;
		logger.log("Index of this class: " + indexOfThisClass);

		int indexOfSuperClass = ByteUtils.getInt(classData, readingIndex);
		readingIndex+=2;
		logger.log("Index of super class: " + indexOfSuperClass);


		List<Integer> interfaceIndices = getInterfaceIndices();
		logger.log("Implements " + interfaceIndices.size()+ " interfaces");
		
		
		
		try {
			fieldPool = readFields();
		} catch (FieldParsingException e) {
			throw new InvalidFileFormatException(e);
		}
		logger.log("The fields are: \n" + fieldPool.getFields());
		
		try {
			methodPool = readMethods();
		} catch (MethodParsingException e) {
			throw new InvalidFileFormatException(e);
		}
		logger.log("The methods are: \n" + methodPool.getMethods());
		try {
			attributePool = getAttributePool();
		} catch (AttributeNameNotFoundException e) {
			throw new InvalidFileFormatException(e);
		}
	}

	private AttributePool getAttributePool() throws AttributeNameNotFoundException {
		int attributeCount = readInt();
		AttributePool pool = new AttributePool();
		for(int i = 0; i < attributeCount; i++){
			int attrNameIndex = readInt();
			String attrName = null;
			try {
				attrName = (String) constantPool.getEntryAtIndex(attrNameIndex).getValue();
			} catch (MemberNotContainedException e) {
				throw new AttributeNameNotFoundException(e);
			}
			byte[] intValue = new byte[4];
			for(int k = 0; k < 4; k++){
				intValue[k] = classData[readingIndex+k];
			}
			int attrLength = ByteBuffer.wrap(intValue).getInt();
			readingIndex+=4;
			System.out.println(attrName);
			Attribute attribte = new Attribute(attrName,classData,readingIndex);
			attribte.parseAttribute();
			readingIndex+= attrLength;
		}
		return pool;
	}

	private MethodPool readMethods() throws MethodParsingException{
		MethodPool pool = new MethodPool(this);
		int methodCount = readInt();
		for(int i = 0; i < methodCount; i++){
			String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
			int intFl = Integer.parseInt(fls,2);
			readingIndex+=2;
			
			List<MethodAccessFlag> flags = MethodAccessFlag.getFlags(intFl);
			int nameIndex = readInt();
			String methodName = null;
			try {
				methodName = (String) constantPool.getEntryAtIndex(nameIndex).getValue();
			} catch (MemberNotContainedException e) {
				throw new MethodNameNotFoundException(e);
			}
			int descriptorIndex = readInt();
			String methodDescriptor = null;
			try {
				methodDescriptor = (String) constantPool.getEntryAtIndex(descriptorIndex).getValue();
			} catch (MemberNotContainedException e) {
				throw new MethodDescriptorNotFoundException(e);
			}
			
			AttributePool attributePool = null;
			try {
				attributePool = getAttributePool();
			} catch (AttributeNameNotFoundException e) {
				throw new MethodParsingException(e);
			}
			Method method = new Method(flags,methodName,methodDescriptor,attributePool);
			pool.addMethod(method);
		}
		return pool;
	}

	private FieldPool readFields() throws FieldParsingException {
		int fieldCount = readInt();
		FieldPool pool = new FieldPool(this);
		
		for(int i = 0; i < fieldCount; i++){
			String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
			int intFl = Integer.parseInt(fls,2);
			readingIndex+=2;
			
			List<FieldAccessFlag> flags = FieldAccessFlag.getFlags(intFl);
			
			int nameIndex = readInt();
			String fieldName = null;
			try {
				fieldName = (String) constantPool.getEntryAtIndex(nameIndex).getValue();
			} catch (MemberNotContainedException e) {
				throw new FieldNameNotFoundException(e);
			}
			int descriptorIndex = readInt();
			String fieldDescriptor = null;
			try {
				fieldDescriptor = (String) constantPool.getEntryAtIndex(descriptorIndex).getValue();
			} catch (MemberNotContainedException e) {
				throw new FieldDescriptorNotFoundException(e);
			}
			
			AttributePool attributePool = null;
			try {
				attributePool = getAttributePool();
			} catch (AttributeNameNotFoundException e) {
				throw new FieldParsingException(e);
			}
			Field field = new Field(flags,fieldName,fieldDescriptor,attributePool);
			pool.addField(field);
		}
		return pool;
	}

	private List<Integer> getInterfaceIndices() {
		int interfacesImplemented = ByteUtils.getInt(classData,readingIndex);
		readingIndex+=2;
		List<Integer> interfaceIndices = new ArrayList<Integer>();
		for(int i = 0; i < interfacesImplemented; i++){
			interfaceIndices.add(ByteUtils.getInt(classData, readingIndex));
			readingIndex+=2;
		}
		return interfaceIndices;
	}

	private ConstantPool readConstantPool(int poolSize) throws InvalidConstantPoolMemberException {
		ConstantPool pool = new ConstantPool(this);
		for(int i = 1; i < poolSize; i++){//Read all values in the pool
			int tag = classData[readingIndex];
			readingIndex++;
			ConstantPoolEntry entry = new ConstantPoolEntry(tag, classData, readingIndex);
			entry.parseData();
			pool.addEntry(entry);
			this.readingIndex += entry.getSize();

			if(entry.getEntryType() == Type.LONG || entry.getEntryType() == Type.DOUBLE){
				i++;//Double and long take more space
			}
			entry.setIndex(i);
		}
		return pool;
	}



	private int readInt(){
		int ret = ByteUtils.getInt(classData, readingIndex);
		readingIndex+=2;
		return ret;
	}
	
	


}
