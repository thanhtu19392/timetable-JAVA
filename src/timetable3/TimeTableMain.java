package timetable3;

import java.io.IOException;
import java.util.Scanner;


public class TimeTableMain {

	public static void main(String[] args) throws IOException {
		System.out.println("Entrez le nom du fichier de sortie 'bfa' or 'mido'? puis tapez sur la touche return");
		Scanner scan = new Scanner(System.in);
		String filename = scan.nextLine();
		
		String name = "data/" + filename +".csv";
		System.out.println("Read file " + name);
		
		//Question 1,2,3
		System.out.println("------Creating a list of teacher giving lecture in alphabetical order-----");
		CountOcurrences orderCountOcurrences = new CountOcurrences();
		orderCountOcurrences.alphabeticOrder(name);
			
		//Question 4 
		System.out.println("---sort teacher by number of lectures---");
		SortNumLecture sortNumLecture = new SortNumLecture();
		sortNumLecture.sortNumberLecture(name);
		
		//Question 5
		System.out.println("--------Start creating time table----------");
		System.out.println("Veuillez choisir le semester: 1 or 2? ");
		String text = scan.nextLine();
		String sem = text;
		Initialization initialize=new Initialization();
		initialize.readInput(filename, sem);
	}
}
