package site.shadyside.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributePool {
	private List<Attribute> attributes;
	public AttributePool() {
		this.attributes = new ArrayList<Attribute>();
	}
	
	public void addAttribute(Attribute attr){
		this.attributes.add(attr);
	}
	public List<Attribute> getAttributes(){
		return attributes;
	}
}
