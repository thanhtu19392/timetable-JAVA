package timetable3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SortNumLecture  {
	public void sortNumberLecture(String name) throws IOException{
		//String name = "data/mido.csv";
		//System.out.println("Read file " + name);

		//create new object classes (MIDO or BFA)
		getCoursesData mido = new getCoursesData(name);  

		List<String> myList = new ArrayList<>();
		for (int i = 0; i < mido.courseData.size(); i++){
			myList.add(mido.courseData.get(i).get(4));
		}
		Map<String, Integer> unsortedMap = countTeacher(myList);
		System.out.println("Unsorted Map of teacher : " + unsortedMap);
		Map<String, Integer> sortedMap = sortMapByValue(unsortedMap);
		System.out.println("sorted Map of teacher:  " + sortedMap);   
		
		FileWriter writer = new FileWriter("sortedNumberLecture.txt");
		writer.append("sorted Map of teacher following number of lectures : ");
		for(Entry<String, Integer> entry : sortedMap.entrySet()){
			//System.out.println("\n"+ entry.getKey() + " has taught " + entry.getValue() + " lectures ,");
		    writer.append("\n"+ entry.getKey() + " has taught " + entry.getValue() + " lectures ,");
		}
		writer.flush();
		writer.close();
	}
		
	private static Map<String, Integer> sortMapByValue(Map<String, Integer> unsortedMap) {
		ValueComparator bvc = new ValueComparator(unsortedMap);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
        sortedMap.putAll(unsortedMap);
        return sortedMap;
	    }
		
	private static Map<String, Integer> countTeacher(List<String> myList) {
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		for (String evnt : myList) {
			if (!myMap.containsKey(evnt))
				myMap.put(evnt, 1);
			else {
				myMap.put(evnt, myMap.get(evnt) + 1);
			}
		}
		return myMap;
	}
		
}	

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}
