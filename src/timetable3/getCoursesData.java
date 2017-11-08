package timetable3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class getCoursesData {
	public String fileName;
	public Set<String> listSubject;
	public Set<String> listDiplome;
	public ArrayList<ArrayList<String>> courseData;
	public Set<String> listTeacher;
	public Set<String> listGroupe;
		
	public getCoursesData(String fileName){
		this.fileName = fileName;
		courseData = getCoursesData();
		listDiplome = getDiplome();
		listSubject = getSubject();
		listTeacher = getTeacher();
		listGroupe = getGroupe();
	}
	
	public Set<String> getTeacher() {
		ArrayList<ArrayList<String>> l = getCoursesData();
		Set<String> listTeacher = new HashSet<String>();
		for(int i = 0 ; i < l.size(); i++){
			ArrayList<String> line = l.get(i);
			for (int j=0;j< line.size();j++){
				listTeacher.add(line.get(4));
			}
		}
		return(listTeacher);
	}
	
	public Set<String> getGroupe() {
		ArrayList<ArrayList<String>> l = getCoursesData();
		Set<String> listGroupe = new HashSet<String>();
		for(int i = 0 ; i < l.size(); i++){
			if (l.get(i).size() == 6) {
				listGroupe.add(l.get(i).get(5));}
			else {
				listGroupe.add("0");
			}
		}
		return(listGroupe);
	}
	
	public Set<String> getSubject(){
		ArrayList<ArrayList<String>> l = getCoursesData();
		Set<String> listSubject = new HashSet<String>();
		for(int i = 0 ; i < l.size(); i++){
			listSubject.add(l.get(i).get(3));
		}
		return(listSubject);
	}
	
	public Set<String> getDiplome(){
		ArrayList<ArrayList<String>> l = getCoursesData();
		Set<String> listDiplome = new HashSet<String>();
		for(int i = 0 ; i < l.size(); i++){
			ArrayList<String> line = l.get(i);
			for (int j=0;j< line.size();j++){
				listDiplome.add(line.get(1));
			}
		}
		return(listDiplome);
	}
	
	public ArrayList<ArrayList<String>> getCoursesData() {
		ArrayList<ArrayList<String>> listReturn = new ArrayList<ArrayList<String>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
			String line = reader.readLine();
			
			while (line != null) {
				if (line.charAt(0) != '%') {
					ArrayList<String> myList = new ArrayList<String>(Arrays.asList(line.split(";")));
					listReturn.add(myList);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (
		IOException e) {
			System.err.println("Error reading the input file " + this.fileName + "\n" + e);
		}
		return (listReturn);
	}
}
					