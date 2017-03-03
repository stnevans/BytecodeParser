package site.shadyside.bytecodeparser.commandline;

import java.util.HashMap;
import java.util.Map;

public class OptionParser {
	private Map<String,String> argumentToValueMap;
	public OptionParser(String[] args) {
		argumentToValueMap = new HashMap<String,String>();
		StringBuilder argBuilder = new StringBuilder();
		for(String arg : args){
			argBuilder.append(arg + " ");
		}
		String[] argumentsByDash = argBuilder.toString().substring(1).split("-");
		//Substring at one due to the way String#split works. 
		//Splitting the string "-f filepath" with "-" would result in [,f filepath]
		for(int i = 0; i < argumentsByDash.length; i++){
			argumentsByDash[i] = argumentsByDash[i].trim();
			if(argumentsByDash[i].contains(" ")){
				
				if(argumentsByDash[i].contains("\"")){//If the argument has a quotation mark
					String[] argSwitch =  argumentsByDash[i].split(" ");
					String arg = "";
					for(int c = 1; c < argSwitch.length; c++){
						arg += argSwitch[c] + " ";
					}
					arg = arg.replace("\"", "");
					argumentToValueMap.put(argSwitch[0], arg);
				}else{
					String[] argSwitch =  argumentsByDash[i].split(" ");
					argumentToValueMap.put(argSwitch[0], argSwitch[1]);	
				}
			}else{
				argumentToValueMap.put(argumentsByDash[i], null);
			}
		}
		
		
	}
	
	public String getSwitch(String arg, String defaultCase){
		try{
			String switchResult = argumentToValueMap.get(arg);
			return switchResult != null ? switchResult : defaultCase;
		}catch(Exception e){
			return defaultCase;
		}
	}
	
	public boolean isSwitchSet(String switchToFind){
		return argumentToValueMap.containsKey(switchToFind);
	}
}
