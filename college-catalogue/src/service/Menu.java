package service;

import java.text.ParseException;
import java.util.Scanner;

public class Menu {
    private static final String menuText = """
Select option:
1) Add student
2) List data of student
3) Set group/series representative
4) List students in group or series
5) Add course taught to professor
6) Add grade to student
7) Show yearly average grade of student
8) List all courses of a given subject
9) Show average grade of series
10) Add specialization to student
0) Exit
""";
    private static final Scanner input = new Scanner(System.in);

    public static void printMenuText() {
        System.out.println(menuText);

    }
    public static void runMenu() throws ParseException {
        boolean run = true;
        while(run) {
            printMenuText();
            String option = input.nextLine();
            switch(option) {
                case "1" -> Service.addStudent();
                case "2" -> Service.listStudent();
                case "3" -> Service.setRepresentative();
                case "4" -> Service.listStudentsInGroupOrSeries();
                case "5" -> Service.addCourseToProfessor();
                case "6" -> Service.addGradeToStudent();
                case "7" -> Service.showYearlyAverageOfStudent();
                case "8" -> Service.listCoursesOfSubject();
                case "9" -> Service.showAverageOfSeries();
                case "10" -> Service.addSpecializationToStudent();
                case "0" -> run = false;
                default -> System.out.println("Option not available!");
            }
        }
    }
}
