package timetable3;


public class StudentGroups {
	
	private String name;
	private int noOfLecturePerWeek;
	private String subjectName;
	private String typeGroupe;
	private String department;

	public StudentGroups(String string, int numberOfLectures,/* int i, ArrayList<Combination> combs,*/ String subject, String type, String dept) {
		this.setName(string);
		this.setNoOfLecturePerWeek(numberOfLectures);
		//this.setCombination(combs);
		//this.setSize(i);
		this.subjectName=subject;
		this.typeGroupe= type;
		this.setDepartment(dept);
	}

	
	public int getNoOfLecturePerWeek() {
		return noOfLecturePerWeek;
	}

	public void setNoOfLecturePerWeek(int noOfLecturePerWeek) {
		this.noOfLecturePerWeek = noOfLecturePerWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String typeGroupe() {
		return typeGroupe;
	}

	public void setTypeGroupe(String typeGroupe) {
		this.typeGroupe = typeGroupe;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
