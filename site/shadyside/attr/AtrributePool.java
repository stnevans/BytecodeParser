package site.shadyside.attr;

import java.util.ArrayList;
import java.util.List;

public class AtrributePool {
	private List<Attribute> attributes;
	public AtrributePool() {
		this.attributes = new ArrayList<Attribute>();
	}
	
	public void addAttribute(Attribute attr){
		this.attributes.add(attr);
	}
	public List<Attribute> getAttributes(){
		return attributes;
	}
}
