package service;

import enums.AuditOption;
import enums.AuditUtilClass;
import interfaces.AuditableUtil;
import enums.Year;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Service implements AuditableUtil {
    // 0) incarcare date
    public static void loadData() throws IOException, ParseException {
        System.out.println("Loading data...");
        CSV_Reader.getInstance().readObjects();
        System.out.println("Data loaded!");
        AuditableUtil.auditUtil(AuditOption.SERVICE_LOAD_DATA, AuditUtilClass.SERVICE);
    }
    // 1) adaugare student
    public static void addStudent() throws ParseException {
        System.out.println("Adding student:");
        Scanner in = new Scanner(System.in);
        System.out.println("First name:");
        String first_name = in.nextLine();
        System.out.println("Surname:");
        String surname = in.nextLine();
        System.out.println("Date of birth (dd/mm/yyyy):");
        String dateOfBirth = in.nextLine();
        System.out.println("CNP:");
        String cnp = in.nextLine();
        System.out.println("Group ID:");
        String groupID = in.nextLine();
        AuxService.addStudent(first_name, surname, dateOfBirth, cnp, groupID);
        AuditableUtil.auditUtil(AuditOption.SERVICE_ADD_STUDENT, AuditUtilClass.SERVICE);
    }
    // 2) listare informatii despre un student
    public static void listStudent(){
        System.out.println("Listing data for student:");
        AuxService.listStudent();
        AuditableUtil.auditUtil(AuditOption.SERVICE_LIST_STUDENT, AuditUtilClass.SERVICE);
    }
    // 3) setare reprezentant serie / grupa
    public static void setRepresentative(){
        System.out.println("Set representative:");
        AuxService.setRepresentative();
        AuditableUtil.auditUtil(AuditOption.SERVICE_SET_REPRESENTATIVE, AuditUtilClass.SERVICE);
    }
    // 4) Listare studenti dintr-o serie / grupa
    public static void listStudentsInGroupOrSeries() {
        System.out.println("Listing students in group/series:");
        AuxService.listStudentsInUnit();
        AuditableUtil.auditUtil(AuditOption.SERVICE_LIST_STUDENTS_IN_UNIT, AuditUtilClass.SERVICE);
    }
    // 5) Adaugare curs predat unui profesor
    public static void addCourseToProfessor() {
        System.out.println("Adding course taught to professor:");
        System.out.println("Professor: ");
        Professor p = AuxService.searchProfessorByName();
        if(p == null) return;
        else {
            Course c = AuxService.searchCourseByCode();
            if(c != null) {
                p.addCourseTaught(c);
                System.out.println("Course " + c.getCode() + " added succesfully to " + p.getFullName());
            }
        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_ADD_COURSE_TO_PROFESSOR, AuditUtilClass.SERVICE);
    }
    // 6) Adaugare/editare nota unui student
    public static void addGradeToStudent() {
        System.out.println("Adding grade to student:");
        System.out.println("Student: ");
        Student s = AuxService.searchStudentByName();
        if(s == null) return;
        else {
            System.out.println("Course code: ");
            Course c = AuxService.searchCourseByCode();
            if(c == null) return;
            else {
                System.out.println("Grade: ");
                Scanner in = new Scanner(System.in);
                float score = in.nextFloat();
                AuxService.addGrade(s, c, score);
                System.out.println("Grade added succesfully!");
            }
        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_ADD_GRADE_TO_STUDENT, AuditUtilClass.SERVICE);
    }
    // 7) Calculare medie student pe un an
    public static void showYearlyAverageOfStudent() {
        System.out.println("Average grade of student: ");
        System.out.println("Student: ");
        Student s = AuxService.searchStudentByName();
        if(s == null) return;
        else {
            System.out.println("College year to calculate average for: ");
            Year year = AuxService.searchYear();
            float average = s.calculateAverage(year);
            if(average == -1) {
                System.out.println(s.getFullName() + " has no grades in year " + year.name());
            }
            else {
                System.out.println("Average for student " + s.getFullName() + ", year " + year.name() + ": " + average);
            }
        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_SHOW_YEARLY_AVERAGE, AuditUtilClass.SERVICE);
    }
    // 8) Media pe o anumita serie
    public static void showAverageOfSeries() {
        System.out.println("Average grade of series: ");
        StudentSeries s = AuxService.searchSeriesByCode();
        if(s != null) {
            float nrStudents = 0;
            float totalGrade = 0;
            float average;
            for(Student student : s.getStudents()) {
                average = student.calculateAverage();
                if(average != -1) {
                    nrStudents++;
                    totalGrade += average;
                }
            }
            if(nrStudents == 0) {
                System.out.println("No students in series " + s.getId() + " have grades!");
            }
            else {
                System.out.println("Average grade of series " + s.getId() + " : " +
                        Math.round(totalGrade / nrStudents * 100) / (float)100);
            }

        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_SHOW_AVERAGE_IN_SERIES, AuditUtilClass.SERVICE);

    }
    // 9) Listare toate cursurile unde a fost predata o anumita materie
    public static void listCoursesOfSubject() {
        System.out.println("Listing courses of subject: ");
        Subject s = AuxService.searchSubjectByCode();
        if(s != null) {
            System.out.println("Courses for " + s.getName() + " (" + s.getCode() + "):");
            for (Course c : AuxService.getCourses().values()) {
                if(c.getSubject() == s) {
                    System.out.println("\t " + c.getCode() + " Year " + c.getYear() + " College year " + c.getCollegeYear()
                    + " Semester " + c.getSemester());
                }
            }
        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_LIST_COURSES_OF_SUBJECT, AuditUtilClass.SERVICE);
    }

    // 10) Inscriere student la o specializare
    public static void addSpecializationToStudent() {
        System.out.println("Adding specialization to student: ");
        Student student = AuxService.searchStudentByName();
        if(student != null) {
            Specialization specialization = AuxService.searchSpecializationByCode();
            if(specialization != null) {
                student.addSpecialization(specialization);
                System.out.println("Specialization added succesfully!");
            }
        }
        AuditableUtil.auditUtil(AuditOption.SERVICE_ADD_SPECIALIZATION_TO_STUDENT, AuditUtilClass.SERVICE);
    }


    public static void end() throws IOException {
        CSV_Writer.close();
    }
}
