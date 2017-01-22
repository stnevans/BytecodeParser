package site.shadyside.constantpool;

import java.util.ArrayList;
import java.util.List;

import site.shadyside.exception.MemberNotContainedException;
import site.shadyside.parse.classParser.ClassParser;

public class ConstantPool {
	private ClassParser parentClass;
	private List<ConstantPoolEntry> members;
	public ConstantPool(ClassParser classParser) {
		this.parentClass = classParser;
		this.members = new ArrayList<ConstantPoolEntry>();
	}
	
	public void addEntry(ConstantPoolEntry entry){
		this.members.add(entry);
	}
	
	public ConstantPoolEntry getEntryAtIndex(int index) throws MemberNotContainedException{
		for(ConstantPoolEntry member :  members){
			if(member.getIndex() == index){
				return member;
			}
		}
		throw new MemberNotContainedException();
	}
	

}
