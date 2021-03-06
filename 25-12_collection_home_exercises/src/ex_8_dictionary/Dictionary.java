package ex_8_dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Dictionary {
	
	
	private Map<String,String> map = new HashMap<>();
	
	public void addEntry(String entry,String definition) throws DictionaryException{
	
		if(entry == null || entry.length() == 0 || definition == null || definition.length()==0) {
			throw new DictionaryException("addEntry failed, invailed entry  or definition");
		}
		if (map.containsKey(entry)) {
			throw new DictionaryException("addEntry failed, entery already exsist");
		}
		map.put(entry, definition);
		
		
	}
	public String getDefinition(String entry) {
		return map.get(entry);
	}
	
	
	public void editEntry(String entry, String definition) throws DictionaryException {
		if(entry == null || entry.length() == 0 || definition == null || definition.length()==0) {
			throw new DictionaryException("addEntry failed, invailed entry  or definition");
		}
		if(!map.containsKey(entry)) {
			throw new DictionaryException("editEntry failed, entry not exists");
		}
		map.put(entry, definition);
	}
	
	
	public String deleteEntry(String entry) {
		return map.remove(entry);
		
	}
	
	
	public Set<String> getAllEntrySorted(){
		Set<String> set = new TreeSet<String>();
		set = map.keySet();
		return set;
		
	}
	
	
	
	
}
