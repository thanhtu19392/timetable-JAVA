package timetable3;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class CountOcurrences {
		
	public void alphabeticOrder(String name) throws IOException{
		//String name = "data/mido.csv";
		//System.out.println("Read file " + name);

		//create new object classes (MIDO or BFA)
		getCoursesData mido = new getCoursesData(name);  

		//Number of subjects, Diploma and Teachers
		System.out.println("Number 	of Teacher for mido : " + mido.listTeacher.size());
		System.out.println("Number 	of Diploma for mido : " + mido.listDiplome.size());
		System.out.println("Number 	of Subject for mido : " + mido.listSubject.size());
		System.out.println("------------------------------------------");
		
		//System.out.println(mido.courseData.get(0).get(5));
		Iterator<String> itDip = mido.listDiplome.iterator();
		while(itDip.hasNext()){
			String element = itDip.next();
			Set<String> listDiplome = new HashSet<String>();
			Set<String> listTeacher = new HashSet<String>(); 
			for(int i = 0; i < mido.courseData.size(); i++){
				if (mido.courseData.get(i).get(1).equals(element)) {
					listDiplome.add(mido.courseData.get(i).get(3));
					listTeacher.add(mido.courseData.get(i).get(4));
				}
			}
			System.out.println("number of subject for diploma " + element + " is: " + listDiplome.size());
			System.out.println("number of teacher for diploma " + element + " is: " + listDiplome.size());
		}

		//list of teacher give lectures on alphabetical order
		Set<String> teacherLectureSet =  new TreeSet<String>();
		for (int i = 0; i < mido.courseData.size(); i++){
			if (mido.courseData.get(i).get(0).equals("CM")) {
				teacherLectureSet.add(mido.courseData.get(i).get(4));
			}
		}
		System.out.println("------------------------------------------");
		System.out.println("number of teacher for lecture : " + teacherLectureSet.size());
		System.out.println("List of teacher giving lecture in alphabetical order : ");
		System.out.println(teacherLectureSet);
		
		FileWriter writer = new FileWriter("alphabeticOrder.txt");
		writer.append("List of teacher giving lecture in alphabetical order : ");
		Iterator<String> itTeacherIterator = teacherLectureSet.iterator();
		while (itTeacherIterator.hasNext()) {
			writer.append("\n" + itTeacherIterator.next());	
		}
		writer.flush();
		writer.close();
	}		
}
