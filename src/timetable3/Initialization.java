package timetable3;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;


public class Initialization {
	
	//this class takes all inputs from a file. courseID, courseName, roomID's, subjects and professors associated with course
	
	private ArrayList<Subject> subjects=new ArrayList<Subject>();
	private ArrayList<Professor> professors=new ArrayList<Professor>();
	private ArrayList<TimeTable> timetables=new ArrayList<TimeTable>();
	private ArrayList<Lecture> lectures=new ArrayList<Lecture>();
	private static ArrayList<String>weekDayNames=new ArrayList<>();
	private static ArrayList<String>lectureTimings=new ArrayList<>();
			
	//reads input from a file.
	
	public void readInput(String filename, String sem) throws IOException{
		
		ArrayList<ClassRoom> classroom=new ArrayList<>();
		String name = "data/" + filename +".csv";
		
		//add rooms 
		if (filename.equals("bfa")){
			ClassRoom roomb1 = new ClassRoom("B101", "CM", "BFA1");
			classroom.add(roomb1);
			ClassRoom roomb2 = new ClassRoom("B102", "CM", "BFA2");
			classroom.add(roomb2);
		}
		else {
			ClassRoom room11 = new ClassRoom("P101", "CM", "DE1");
			classroom.add(room11);
			ClassRoom room12 = new ClassRoom("P102", "TD", "DE1");
			classroom.add(room12);
			ClassRoom room13 = new ClassRoom("P103", "TP", "DE1");
			classroom.add(room13);


			ClassRoom room21 = new ClassRoom("P201", "CM", "DE2");
			classroom.add(room21);
			ClassRoom room22 = new ClassRoom("P202", "TD", "DE2");
			classroom.add(room22);
			ClassRoom room23 = new ClassRoom("P203", "TP", "DE2");
			classroom.add(room23);
			/*
		ClassRoom room221 = new ClassRoom("P204", "TD", "DE2");
		classroom.add(room221);
		ClassRoom room222 = new ClassRoom("P205", "TP", "DE2");
		classroom.add(room222);*/

			ClassRoom room31 = new ClassRoom("P301", "CM", "L3INFO");
			classroom.add(room31);
			ClassRoom room32 = new ClassRoom("P302", "TD", "L3INFO");
			classroom.add(room32);
			ClassRoom room33 = new ClassRoom("P303", "TP", "L3INFO");
			classroom.add(room33);


			ClassRoom room51 = new ClassRoom("P401", "CM", "L3MATHS");
			classroom.add(room51);
			ClassRoom room52 = new ClassRoom("P402", "TD", "L3MATHS");
			classroom.add(room52);
			ClassRoom room53 = new ClassRoom("P403", "TP", "L3MATHS");
			classroom.add(room53);


			ClassRoom room61 = new ClassRoom("P501", "TD", "M1INFO");
			classroom.add(room61);
			ClassRoom room62 = new ClassRoom("P502", "TP", "M1INFO");
			classroom.add(room62);
			ClassRoom room63 = new ClassRoom("P503", "CM", "M1INFO");
			classroom.add(room63);


			ClassRoom room71 = new ClassRoom("P501", "CM", "M1MATHS");
			classroom.add(room71);
			ClassRoom room72 = new ClassRoom("P502", "TP", "M1MATHS");
			classroom.add(room72);
			ClassRoom room73 = new ClassRoom("P503", "TD", "M1MATHS");
			classroom.add(room73);
		}

		getCoursesData mido = new getCoursesData(name);
		
		//add professors
		Iterator<String> itTeacher = mido.listTeacher.iterator();
		while(itTeacher.hasNext()){
			String element = itTeacher.next();
			for(int i = 0; i < mido.courseData.size(); i++){
				if (mido.courseData.get(i).get(4).equals(element) & mido.courseData.get(i).get(2).equals(sem)) {
					professors.add(new Professor(professors.size(), element, mido.courseData.get(i).get(3)));
				}
		}
		}
		createLectures(professors);
		
		TimeTable timetb= new TimeTable(classroom, lectures);
		
		//add subjects
		Iterator<String> itGrp = mido.listGroupe.iterator();
		System.out.println("---------------reading input--------------------");
		int courseid = 1;
		while(itGrp.hasNext()){
			String groupeName = itGrp.next(); 
			String courseName= "Group " + groupeName;

			for(int i = 0; i < mido.courseData.size(); i++){
				if (mido.courseData.get(i).size() < 6 ){
					if (groupeName.equals("0") & mido.courseData.get(i).get(2).equals(sem)){
					subjects.add(new Subject(subjects.size(), mido.courseData.get(i).get(3), 1, mido.courseData.get(i).get(0), mido.courseData.get(i).get(1)));
					}
				}
				else if (mido.courseData.get(i).get(5).equals(groupeName) & mido.courseData.get(i).get(2).equals(sem)) {
					subjects.add(new Subject(subjects.size(), mido.courseData.get(i).get(3), 1, mido.courseData.get(i).get(0), mido.courseData.get(i).get(1)));
				}
			}
			
			//Create new course
			Course course = new Course(courseid, courseName, subjects);
			course.createStudentGroups();
			ArrayList<StudentGroups> studentGroups = course.getStudentGroups();
			timetb.addStudentGroups(studentGroups);
			courseid++;
			subjects.clear();
			}
			
			System.out.println("---------------adding timetable---------------");
			timetb.initializeTimeTable();
			timetb.setSemester(sem);
			timetb.setFileName(filename);
			timetables.add(timetb);

			System.out.println("---------------start populating----------------");
			populateTimeTable(timetb);	
		}
	
	private static void createWeek(){
		String[] weekDaysName=new DateFormatSymbols().getWeekdays();
		for (int i = 1; i < weekDaysName.length; i++) {
	    	if (!(i == Calendar.SUNDAY) & !(i == Calendar.SATURDAY))
	    	weekDayNames.add(weekDaysName[i]);
	    		}
	    	}
	
	private static void createLectureTime(){
		for(int i=9; i<16; i++){
				lectureTimings.add(i+":00"+" TO "+(i+1)+":00");	
		}
	}
	
	public void populateTimeTable(TimeTable timetb) throws IOException{
		TimeTable tempTimetable = timetb;
		ArrayList<ClassRoom> allrooms = tempTimetable.getRoom();
		Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
		while(allroomsIterator.hasNext()){
			ClassRoom room = allroomsIterator.next();
			ArrayList<Day> weekdays = room.getWeek().getWeekDays();
			Collections.shuffle(weekdays);
			Iterator<Day> daysIterator=weekdays.iterator();
			while(daysIterator.hasNext()){
				Day day = daysIterator.next();
				Collections.shuffle(day.getTimeSlot());
			}
		}				
		timetables.add(tempTimetable);

		System.out.println("----------------Done populating--------------");
		System.out.println("------------Starting write to file-------------");
		createWeek();
		createLectureTime();
		writeToExcelFile();
	}
	
	private void createLectures (ArrayList<Professor> professors) {
		
		Iterator<Professor> professorIterator=professors.iterator();
		while(professorIterator.hasNext()){
			Professor professor=professorIterator.next();
			ArrayList<String> subjectsTaught = professor.getSubjectTaught();
			Iterator<String> subjectIterator = subjectsTaught.iterator();
			while(subjectIterator.hasNext()){
				String subject = subjectIterator.next();
				lectures.add(new Lecture (professor, subject));
			}
		}
	}
	
	
	private void writeToExcelFile() throws IOException{

		FileWriter writer = new FileWriter("timetable.csv");
		int i=0;
		Iterator<TimeTable> timetableIterator = timetables.iterator();

		//while(timetableIterator.hasNext()){
		TimeTable currentTimetable = timetableIterator.next();
		
		writer.append("\n Planning of fomation " +currentTimetable.getFileName() + " during semester " + currentTimetable.getSemester());
		writer.append("\n (Diplome||Student Group||Subject||Professor)");
		ArrayList<ClassRoom> allrooms = currentTimetable.getRoom();

		Iterator<ClassRoom> allroomsIterator = allrooms.iterator();

		while(allroomsIterator.hasNext()){
			ClassRoom room = allroomsIterator.next();
			writer.append("\n\n Diplome: " + room.getDepartment() + " , " + room.getTypeRoom() + " ,Room : "+room.getRoomNo());
			ArrayList<Day> weekdays = room.getWeek().getWeekDays();
			Iterator<Day> daysIterator=weekdays.iterator();
			Iterator<String> lectTimeItr = lectureTimings.iterator();
			writer.append("\n\nTimings: ,");
			while(lectTimeItr.hasNext()){
				writer.append(lectTimeItr.next()+",");
			}
			i=0;
			writer.append("\nDays\n");
			while(daysIterator.hasNext() & i < 5){
				Day day = daysIterator.next();
				writer.append(""+weekDayNames.get(i)+",");
				ArrayList<TimeSlot> timeslots = day.getTimeSlot();
				i++;
				for(int k=0; k<timeslots.size();k++){
					if(k==3){
						writer.append("---BREAK---,");
					}
					TimeSlot lecture = timeslots.get(k);
					if(lecture.getLecture()!=null){
						if (lecture.getLecture().getStudentGroup().getName().split("/")[0].equals("Group 0")){
							writer.append("("+lecture.getLecture().getStudentGroup().getDepartment() + "||"+ "CM" + "||" 
									 + lecture.getLecture().getSubject()+"||" +lecture.getLecture().getProfessor().getProfessorName() + ")"+",");
						}
						else {
							writer.append("("+lecture.getLecture().getStudentGroup().getDepartment() + "||"+ lecture.getLecture().getStudentGroup().getName().split("/")[0] + "||" 
									 + lecture.getLecture().getSubject()+"||" +lecture.getLecture().getProfessor().getProfessorName() + ")"+",");
						}
					}

					else{
						writer.append("---Free---,");
					}
				}
				writer.append("\n");
			}
			writer.append("\n");
		}
		writer.flush();
		writer.close();
		}
	

}
