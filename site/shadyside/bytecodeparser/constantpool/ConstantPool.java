package site.shadyside.bytecodeparser.constantpool;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import site.shadyside.bytecodeparser.exception.MemberNotContainedException;
import site.shadyside.bytecodeparser.parse.classParser.ClassParser;

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

	public List<ConstantPoolEntry> getAllByType(ConstantPoolEntry.Type type){
		List<ConstantPoolEntry> tempEntries = new ArrayList<ConstantPoolEntry>();
		for(ConstantPoolEntry entry : members){
			if(entry.getEntryType() == type){
				tempEntries.add(entry);
			}
		}
		return tempEntries;
	}

}
