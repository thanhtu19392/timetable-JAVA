package timetable3;

import java.util.ArrayList;

public class Day {
	private ArrayList <TimeSlot> timeSlot=new ArrayList<TimeSlot>();
	
	public ArrayList<TimeSlot> getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(ArrayList<TimeSlot> timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Day(String inputname){
		for(int i=8; i<20; i++){
			if(i!=12){
				TimeSlot ts=new TimeSlot();
				timeSlot.add(ts);
			}
			
		}
	}
}
