package timetable3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class TimeTable {
	private ArrayList<ClassRoom> rooms = new ArrayList<ClassRoom>();
	private ArrayList<Lecture> classes=new ArrayList<>();
	private ArrayList<StudentGroups> studentGroups=new ArrayList<>(); 
	private Set<ClassRoom> CmRooms = new HashSet<ClassRoom>();
	private Set<ClassRoom> TdRooms = new HashSet<ClassRoom>();
	private Set<ClassRoom> TpRooms = new HashSet<ClassRoom>();
	private ArrayList<StudentGroups> CmStudentGroups=new ArrayList<>(); 
	private ArrayList<StudentGroups> TdStudentGroups=new ArrayList<>(); 
	private ArrayList<StudentGroups> TpStudentGroups=new ArrayList<>(); 
	private String semester;
	private String filename;
	
	public TimeTable(ArrayList<ClassRoom> classroom, ArrayList<Lecture> lectures){
		this.rooms=classroom;
		this.classes=lectures;
	}
	

	public void addStudentGroups(ArrayList<StudentGroups> studentgrps) {
		studentGroups.addAll(studentgrps);
	}
	
	public void initializeTimeTable(){
		for (Iterator<ClassRoom> roomsIterator = rooms.iterator(); roomsIterator.hasNext();) {
			ClassRoom room = roomsIterator.next();
			if(room.getTypeRoom().equals("TD")){
				TdRooms.add(room);
			}
			else if (room.getTypeRoom().equals("TP")) {
				TpRooms.add(room);
			}
			else{
				CmRooms.add(room);
			}
		}
		for (Iterator<StudentGroups> studentGroupIterator = studentGroups.iterator(); studentGroupIterator.hasNext();) {
			StudentGroups studentGroup = studentGroupIterator.next();
			//System.out.println(studentGroup.getName() + "," + studentGroup.typeGroupe()+ ","+ studentGroup.getSubjectName() + "," + studentGroup.getDepartment());
			if(studentGroup.typeGroupe().equals("CM")){
				CmStudentGroups.add(studentGroup);
				//System.out.println(studentGroup.typeGroupe() + "," + studentGroup.getName());
			}
			else if (studentGroup.typeGroupe().equals("TD")) {
				TdStudentGroups.add(studentGroup);
				//System.out.println(studentGroup.typeGroupe() + "," + studentGroup.getName() + "," + studentGroup.getDepartment());
			}
			else{
				TpStudentGroups.add(studentGroup);
			}
		}
		
		
		setTimeTable(CmStudentGroups, CmRooms, "CM");
		setTimeTable(TdStudentGroups, TdRooms, "practical");
		setTimeTable(TpStudentGroups, TpRooms, "practical");
		rooms.clear();
		
		rooms.addAll(CmRooms);
		rooms.addAll(TpRooms);
		rooms.addAll(TdRooms);
	}
	
	public void setTimeTable(ArrayList<StudentGroups> studentGroups2, Set<ClassRoom> cmRooms2, String string) {
		//create list of lectures and place them into best free room 
		Collections.shuffle(studentGroups2);
		Stack<Lecture> lecturesStack=new Stack<Lecture>();
		for (Iterator<StudentGroups> sdtGrpIterator = studentGroups2.iterator(); sdtGrpIterator.hasNext();) {			
			StudentGroups studentGrp = sdtGrpIterator.next();
			String subject = studentGrp.getSubjectName();
			int noOfLectures = studentGrp.getNoOfLecturePerWeek();
			for(int i=0; i< noOfLectures; i++){
				Collections.shuffle(classes);
				Iterator<Lecture> classIterator = classes.iterator();
				while(classIterator.hasNext()){
					Lecture lecture = classIterator.next();
					//System.out.println(lecture.getSubject() + "," + subject);
					if(lecture.getSubject().equalsIgnoreCase(subject)){
						Lecture mainLecture=new Lecture(lecture.getProfessor(), lecture.getSubject());
						mainLecture.setStudentGroup(studentGrp);
						lecturesStack.push(mainLecture);
						break;
					}
				}
			}
		}
		//---------start placing lecture into rooms---------------"
		while(!(lecturesStack.empty())){
			Collections.shuffle(lecturesStack);
			Lecture lecture2 = lecturesStack.pop();
			placeLecture(lecture2, cmRooms2);
		}
		}		
	

	private void placeLecture(Lecture lecture, Set<ClassRoom> cmRooms2) {
		String dept=lecture.getStudentGroup().getDepartment();
		List<ClassRoom> rooms2 = new ArrayList<>(cmRooms2);
		boolean invalid=true;
		ClassRoom room = null;
		Collections.shuffle(rooms2);
		while(invalid){
			room=getBestRoom(rooms2);
			if(room.getDepartment().equalsIgnoreCase(dept)){
				invalid=false;
				Collections.shuffle(rooms2);
				}
			else{
				Collections.shuffle(rooms2);
				}
			}
		ArrayList<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext()){
			Day day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext()){
				TimeSlot lecture2 = timeslotIterator.next();
				if(lecture2.getLecture()==null){
				lecture2.setLecture(lecture);
				return;				
				}
			}
		}		
	}



	private boolean checkOccupiedRoom(ClassRoom tempRoom, List<ClassRoom> rooms2) {
		// check if one room is occupied or not
		for (Iterator<ClassRoom> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			ClassRoom room = roomsIterator.next();
			if(room.equals(tempRoom)){
			ArrayList<Day> weekdays = room.getWeek().getWeekDays();
			Iterator<Day> daysIterator=weekdays.iterator();
			while(daysIterator.hasNext()){
				Day day = daysIterator.next();
				ArrayList<TimeSlot> timeslots = day.getTimeSlot();
				Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
				while(timeslotIterator.hasNext()){
					TimeSlot lecture = timeslotIterator.next();
					if(lecture.getLecture()== null){
						return false;
					}
				}
			}
			return true;
		}		
		}
		return false;
	}

	private ClassRoom getBestRoom(List<ClassRoom> rooms2) {
		//get best dispo room 
		ClassRoom room = null;
		for (Iterator<ClassRoom> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			ClassRoom tempRoom = roomsIterator.next();
			if(!checkOccupiedRoom(tempRoom, rooms2)){
				room = tempRoom;
			}
		}
		return room;
	}

	public ArrayList<ClassRoom> getRoom() {
		return rooms;
	}

	public void setRoom(ArrayList<ClassRoom> room) {
		this.rooms = room;
	}

	public Set<ClassRoom> getPracticalRooms() {
		return CmRooms;
	}

	public void setPracticalRooms(Set<ClassRoom> practicalRooms) {
		this.CmRooms = practicalRooms;
	}
	
	public Set<ClassRoom> getTheoryRooms() {
		return TpRooms;
	}

	public void setTheoryRooms(ArrayList<ClassRoom> theoryRooms) {
		theoryRooms = theoryRooms;
	}

	public ArrayList<StudentGroups> getTheoryStudentGroups() {
		return CmStudentGroups ;
	}

	public void setTheoryStudentGroups(ArrayList<StudentGroups> theoryStudentGroups) {
		this.CmStudentGroups = theoryStudentGroups;
	}

	public ArrayList<StudentGroups> getPracticalStudentGroups() {
		return TpStudentGroups;
	}

	public void setPracticalStudentGroups(ArrayList<StudentGroups> practicalStudentGroups) {
		this.TpStudentGroups = practicalStudentGroups;
	}
	
	public String getSemester() {
		return semester;
	}
	public void setSemester(String sem){
		this.semester = sem;
	}
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename){
		this.filename = filename;
	}
	
}
