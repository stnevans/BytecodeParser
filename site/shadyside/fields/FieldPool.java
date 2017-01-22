package site.shadyside.fields;

import java.util.ArrayList;
import java.util.List;

import site.shadyside.parse.classParser.ClassParser;

public class FieldPool {
	private ClassParser parentClass;
	private List<Field> fields;
	public FieldPool(ClassParser parent){
		this.parentClass = parent;
		fields = new ArrayList<Field>();
	}

	public void addField(Field field) {
		fields.add(field);
	}
	
	public List<Field> getFields(){
		return fields;
	}
}
