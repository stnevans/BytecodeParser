package site.shadyside.bytecodeparser.methods;

import java.util.ArrayList;
import java.util.List;

import site.shadyside.bytecodeparser.parse.classParser.ClassParser;

public class MethodPool {
	private ClassParser parent;
	private List<Method> methods;
	public MethodPool(ClassParser parent){
		this.parent = parent;
		methods = new ArrayList<Method>();
	}
	
	public void addMethod(Method method){
		methods.add(method);
	}
	public List<Method> getMethods(){
		return methods;
	}
}
