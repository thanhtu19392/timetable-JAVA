package timetable3;

import java.util.ArrayList;
import java.util.Iterator;

public class Course {
    
	//represents each group
	//Every group has subjects included
	
	private int courseID;
	private String courseName;//groupName
	private ArrayList<Subject> subjectsIncluded= new ArrayList<Subject>();
	
	
	private ArrayList<StudentGroups> studentGroups=new ArrayList<StudentGroups>();
	
	public ArrayList<StudentGroups> getStudentGroups() {
		return studentGroups;
	}

	public void setStudentGroups(ArrayList<StudentGroups> studentGroups) {
		this.studentGroups = studentGroups;
	}

	Course(int id, String name, ArrayList<Subject> subjects){
		System.out.println("----------------creating new course-----------------");
		this.courseID=id;
		this.courseName=name;
		this.subjectsIncluded=subjects;
	}
	
	public void createStudentGroups(){
		Iterator<Subject> subjectIterator=subjectsIncluded.iterator();
		while(subjectIterator.hasNext()){
			Subject subject=subjectIterator.next();
			StudentGroups studentGroup=new StudentGroups(this.courseName+"/"+subject.getSubjectName(), subject.getNumberOfLecturesPerWeek(), subject.getSubjectName(), subject.typeSubject(), subject.getDepartment());
			studentGroups.add(studentGroup);
		}
	}
		
	
	
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ArrayList<Subject> getSubjectsTaught() {
		return subjectsIncluded;
	}
	public void setSubjectsTaught(ArrayList<Subject> subjectsTaught) {
		this.subjectsIncluded = subjectsTaught;
	}
	
}
