package timetable3;

public class ClassRoom {
	
	//represents each classroom
	
private String roomID;
private String typeRoom;
private Week week;
private String department;

public ClassRoom(String id, String typeRoom, String dept){
	this.roomID=id;
	this.setWeek(new Week());
	this.typeRoom= typeRoom;
	this.department=dept;
}


public String getTypeRoom() {
	return typeRoom;
}
public void setTypeRoom(String typeRoom) {
	this.typeRoom = typeRoom;
}

public String getRoomNo() {
	return roomID;
}
public void setRoomNo(String roomNo) {
	this.roomID = roomNo;
}

public Week getWeek() {
	return week;
}

public void setWeek(Week week) {
	this.week = week;
}

public String getDepartment() {
	return department;
}


public void setDepartment(String department) {
	this.department = department;
}
}
