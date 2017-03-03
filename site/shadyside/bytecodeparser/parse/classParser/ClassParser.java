package site.shadyside.bytecodeparser.parse.classParser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import site.shadyside.bytecodeparser.attributes.Attribute;
import site.shadyside.bytecodeparser.attributes.AttributePool;
import site.shadyside.bytecodeparser.constantpool.ConstantPool;
import site.shadyside.bytecodeparser.constantpool.ConstantPoolEntry;
import site.shadyside.bytecodeparser.constantpool.ConstantPoolEntry.Type;
import site.shadyside.bytecodeparser.exception.InvalidConstantPoolMemberException;
import site.shadyside.bytecodeparser.exception.InvalidFileFormatException;
import site.shadyside.bytecodeparser.exception.MemberNotContainedException;
import site.shadyside.bytecodeparser.exception.attribute.AttributeNameNotFoundException;
import site.shadyside.bytecodeparser.exception.fields.FieldDescriptorNotFoundException;
import site.shadyside.bytecodeparser.exception.fields.FieldNameNotFoundException;
import site.shadyside.bytecodeparser.exception.fields.FieldParsingException;
import site.shadyside.bytecodeparser.exception.methods.MethodDescriptorNotFoundException;
import site.shadyside.bytecodeparser.exception.methods.MethodNameNotFoundException;
import site.shadyside.bytecodeparser.exception.methods.MethodParsingException;
import site.shadyside.bytecodeparser.fields.Field;
import site.shadyside.bytecodeparser.fields.FieldPool;
import site.shadyside.bytecodeparser.flags.ClassAccessFlag;
import site.shadyside.bytecodeparser.flags.FieldAccessFlag;
import site.shadyside.bytecodeparser.flags.MethodAccessFlag;
import site.shadyside.bytecodeparser.logger.Logger;
import site.shadyside.bytecodeparser.methods.Method;
import site.shadyside.bytecodeparser.methods.MethodPool;
import site.shadyside.bytecodeparser.utils.ByteUtils;

/**
 * 
 * @author Stuart Nevans Locke
 *
 */
public class ClassParser {
	private byte[] classData;
	private int readingIndex;
	private Path pathToClassFile;
	
	private ConstantPool constantPool;
	private FieldPool fieldPool;
	private MethodPool methodPool;
	private AttributePool attributePool;
	List<ClassAccessFlag> accessFlags;
	
	public ClassParser(Path pathToClassFile) throws IOException{
		this.pathToClassFile = pathToClassFile;
		classData = Files.readAllBytes(pathToClassFile);
		parse();
	}

	public ClassParser(byte[] bytes, Path path) throws IOException {
		this.pathToClassFile = path;
		classData = bytes;
		parse();
	}

	/**
	 * Parses the class file into ConstantPool, MethodPool, FieldPool, and AttributePool. 
	 * 
	 * @throws InvalidFileFormatException if parsing fails for some reason
	 */
	public void parse() throws InvalidFileFormatException{
		Logger logger = Logger.getLogger();
		logger.log("Parsing class file " + pathToClassFile.toString());
		if(!(classData[0] == -54 && classData[1] == -2 && classData[2] == -70 && classData[3] == -66)){
			throw new InvalidFileFormatException("The magic bytes of " + pathToClassFile.toString() + " are incorrect.");
		}
		logger.log("The magic bytes are correct.");
		
		try {
			constantPool = readConstantPool();
		} catch (InvalidConstantPoolMemberException e) {
			logger.log("[ERROR] : Invalid Class Pool Member Found");
			throw new InvalidFileFormatException(e);
		}
		logger.log("Constant Pool Parsing Complete");

		
		String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
		int intFl = Integer.parseInt(fls,2);//TODO check readInt()
		readingIndex+=2;

		accessFlags = ClassAccessFlag.getFlags(intFl);
		logger.log("Access flags: " + accessFlags);

		int indexOfThisClass = readInt();
		logger.log("Index of this class: " + indexOfThisClass);

		int indexOfSuperClass = readInt();
		logger.log("Index of super class: " + indexOfSuperClass);


		List<Integer> interfaceIndices = getInterfaceIndices();
		logger.log("Implements " + interfaceIndices.size()+ " interfaces");
		
		
		try {
			fieldPool = readFieldPool();
		} catch (FieldParsingException e) {
			throw new InvalidFileFormatException(e);
		}
		logger.log("The fields are: \n" + fieldPool.getFields());
		
		try {
			methodPool = readMethodPool();
		} catch (MethodParsingException e) {
			throw new InvalidFileFormatException(e);
		}
		logger.log("The methods are: \n" + methodPool.getMethods());
		try {
			attributePool = readAttributePool();
		} catch (AttributeNameNotFoundException e) {
			throw new InvalidFileFormatException(e);
		}
		logger.log("The attributes are \n" + attributePool.getAttributes());
	}
	/**
	 * Used to read an AttributePool, requires the readingIndex to be the start of the pool
	 * @return an AttributePool found at readingIndex
	 * @throws AttributeNameNotFoundException if an Attribute does not have a corresponding entry in the ConstantPool with a UTF-8 string its name
	 */
	private AttributePool readAttributePool() throws AttributeNameNotFoundException {
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
			Attribute attribte = new Attribute(attrName,classData,readingIndex,constantPool);
			attribte.parseAttribute();
			readingIndex+= attrLength;
		}
		return pool;
	}

	private MethodPool readMethodPool() throws MethodParsingException{
		MethodPool pool = new MethodPool(this);
		int methodCount = readInt();
		for(int i = 0; i < methodCount; i++){
			String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
			int intFl = Integer.parseInt(fls,2);
			readingIndex+=2; //TODO
			
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
				attributePool = readAttributePool();
			} catch (AttributeNameNotFoundException e) {
				throw new MethodParsingException(e);
			}
			Method method = new Method(flags,methodName,methodDescriptor,attributePool);
			pool.addMethod(method);
		}
		return pool;
	}

	private FieldPool readFieldPool() throws FieldParsingException {
		int fieldCount = readInt();
		FieldPool pool = new FieldPool(this);
		
		for(int i = 0; i < fieldCount; i++){
			String fls = String.format("%8s", Integer.toBinaryString(classData[readingIndex] & 0xFF)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(classData[readingIndex+1] & 0xFF)).replace(' ', '0');
			int intFl = Integer.parseInt(fls,2);
			readingIndex+=2; //TODO
			
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
				attributePool = readAttributePool();
			} catch (AttributeNameNotFoundException e) {
				throw new FieldParsingException(e);
			}
			Field field = new Field(flags,fieldName,fieldDescriptor,attributePool);
			pool.addField(field);
		}
		return pool;
	}

	private List<Integer> getInterfaceIndices() {
		int interfacesImplemented = readInt();
		List<Integer> interfaceIndices = new ArrayList<Integer>();
		for(int i = 0; i < interfacesImplemented; i++){
			interfaceIndices.add(readInt());
		}
		return interfaceIndices;
	}

	private ConstantPool readConstantPool() throws InvalidConstantPoolMemberException {
		readingIndex = 8;
		int poolSize = readInt();
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


	/**
	 * Reads a two byte long int, wrapper for ByteUtil.getInt(Lbyte, I)
	 * @return
	 */
	private int readInt(){
		int ret = ByteUtils.getInt(classData, readingIndex);
		readingIndex+=2;
		return ret;
	}

	public ConstantPool getConstantPool() {
		return constantPool;
	}

	public FieldPool getFieldPool() {
		return fieldPool;
	}

	public MethodPool getMethodPool() {
		return methodPool;
	}

	public AttributePool getAttributePool() {
		return attributePool;
	}
}