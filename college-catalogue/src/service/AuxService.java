package service;

import enums.*;
import exceptions.CNP_Exception;
import exceptions.InvalidGradeException;
import interfaces.AuditableUtil;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuxService implements AuditableUtil {
    private static TreeMap<String, Specialization> specializations = new TreeMap<>();
    private static TreeMap<String, StudentSeries> series = new TreeMap<>();
    private static TreeMap<String, StudentGroup> groups = new TreeMap<>();
    private static HashMap<Long, Student> students = new HashMap<>(); //studenti in functie de cnp
    private static HashMap<Long, Professor> professors = new HashMap<>();
    private static TreeMap<String, Subject> subjects = new TreeMap<>();
    private static TreeMap<String, Course> courses = new TreeMap<>();
    private static TreeMap<Attendance, Grade> grades = new TreeMap<>();
    private static TreeSet<Attendance> attendances = new TreeSet<>();


    private AuxService() {
    }

    public static TreeMap<String, Specialization> getSpecializations() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_SPECIALIZATIONS, AuditUtilClass.AUX);
        return specializations;
    }

    public static TreeMap<String, StudentSeries> getSeries() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_SERIES, AuditUtilClass.AUX);
        return series;
    }

    public static TreeMap<String, StudentGroup> getGroups() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_GROUPS, AuditUtilClass.AUX);
        return groups;
    }

    public static HashMap<Long, Student> getStudents() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_STUDENTS, AuditUtilClass.AUX);
        return students;
    }

    public static HashMap<Long, Professor> getProfessors() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_PROFESSORS, AuditUtilClass.AUX);
        return professors;
    }

    public static TreeMap<String, Subject> getSubjects() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_SUBJECTS, AuditUtilClass.AUX);
        return subjects;
    }

    public static TreeMap<String, Course> getCourses() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_COURSES, AuditUtilClass.AUX);
        return courses;
    }

    public static TreeMap<Attendance, Grade> getGrades() {
        AuditableUtil.auditUtil(AuditOption.AUX_GET_GRADES, AuditUtilClass.AUX);
        return grades;
    }

    // Polimorfism prin supraincarcarea functiilor de adaugare: pentru a putea adauga in toate cazurile:
    // a) obiect trimis direct ca parametru
    // b) membrii obiectului trimise ca parametri de tipul de date corespunzator
    // c) membrii obiectului trimise drept stringuri
    public static void addSpecialization(Specialization specialization) {
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_SPECIALIZATION, AuditUtilClass.AUX);
        specializations.put(specialization.getCode(), specialization);
    }
    public static void addSpecialization(String code, String name, int nr_years, Degree degree) {
        addSpecialization(new Specialization(code, name, nr_years, degree));
    }
    public static void addSpecialization(String code, String name, String nr_years, String degree) {
        int new_nr_years = Integer.parseInt(nr_years);
        Degree new_degree = Degree.valueOf(degree);
        addSpecialization(code, name, new_nr_years, new_degree);
    }
    public static void addSpecialization() {
        Scanner in = new Scanner(System.in);
        System.out.println("Specialization code: ");
        String code = in.nextLine();
        System.out.println("Name: ");
        String name = in.nextLine();
        System.out.println("Number of years: ");
        String nr_years = in.nextLine();
        System.out.println("Degree code (BACHELOR/MASTER/PHD): ");
        String degree = in.nextLine();
        addSpecialization(code, name, nr_years, degree);
    }
    public static void addSeries(StudentSeries studentSeries) {
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_SERIES, AuditUtilClass.AUX);
        series.put(studentSeries.getId(), studentSeries);
    }
    public static void addSeries(String id, int number, int year, Specialization specialization) {
        addSeries(new StudentSeries(id, number, year, specialization));
    }
    public static void addSeries(String id, String number, String year, String specialization_code) {
        int new_number = Integer.parseInt(number);
        int new_year = Integer.parseInt(year);
        Specialization specialization = specializations.get(specialization_code);
        addSeries(id, new_number, new_year, specialization);
    }
    public static void addSeries() {
        Scanner in = new Scanner(System.in);
        System.out.println("Series ID: ");
        String id = in.nextLine();
        System.out.println("Series number: ");
        String name = in.nextLine();
        System.out.println("Calendaristic year: ");
        String year = in.nextLine();
        System.out.println("Specialization code: ");
        String specialization_code = in.nextLine();
        addSeries(id, name, year, specialization_code);
    }
    public static void addGroupToSeries(StudentGroup studentGroup, StudentSeries studentSeries) {
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_GROUP_TO_SERIES, AuditUtilClass.AUX);
        studentSeries.addGroup(studentGroup);
        studentSeries.getStudents().addAll(studentGroup.getStudents());
    }
    public static void addGroup(StudentGroup studentGroup) {
        groups.put(studentGroup.getId(), studentGroup);
        addGroupToSeries(studentGroup, studentGroup.getSeries());
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_GROUP, AuditUtilClass.AUX);
    }
    public static void addGroup(String id, int number, int year,
                                Specialization specialization, StudentSeries studentSeries) {
        addGroup(new StudentGroup(id, number, year, specialization, studentSeries));
    }
    public static void addGroup(String id, String number, String year,
                                String specializationCode, String studentSeriesID) {
        int new_number = Integer.parseInt(number);
        int new_year = Integer.parseInt(year);
        Specialization specialization = specializations.get(specializationCode);
        StudentSeries studentSeries = series.get(studentSeriesID);
        addGroup(id, new_number, new_year, specialization, studentSeries);
    }
    public static void addStudent(Student student) {
        students.put(student.getCNP(), student);
        student.getGroup().addStudent(student);
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_STUDENT, AuditUtilClass.AUX);

    }
    public static void addStudent(String first_name, String surname, Date dateOfBirth, long cnp,
                                  StudentGroup group) {
        addStudent(new Student(first_name, surname, dateOfBirth, cnp, group));
    }
    public static void addStudent(String first_name, String surname, String dateOfBirth, String cnp, String groupID) throws ParseException {
        Date new_date = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
        long new_cnp = Long.parseLong(cnp);
        StudentGroup new_group = groups.get(groupID);
        addStudent(first_name, surname, new_date, new_cnp, new_group);
    }

    public static void addProfessor(Professor professor) {
        professors.put(professor.getCNP(), professor);
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_PROFESSOR, AuditUtilClass.AUX);
    }
    public static void addProfessor(String first_name, String surname, Date dateOfBirth, long cnp) {
        addProfessor(new Professor(first_name, surname, dateOfBirth, cnp));
    }
    public static void addProfessor(String first_name, String surname, String dateOfBirth, String cnp) throws ParseException {
        Date new_date = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
        long new_cnp = Long.parseLong(cnp);
        addProfessor(first_name, surname, new_date, new_cnp);
    }


    public static void addSubject(Subject subject) {
        subjects.put(subject.getCode(), subject);
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_SUBJECT, AuditUtilClass.AUX);
    }
    public static void addSubject(String code, String name, int credits,
                                  boolean participatesInTotalGrade) {
        addSubject(new Subject(code, name, credits, participatesInTotalGrade));
    }
    public static void addSubject(String code, String name, String credits,
                                  String participatesInTotalGrade) {
        int new_credits = Integer.parseInt(credits);
        boolean new_bool = Boolean.parseBoolean(participatesInTotalGrade);
        addSubject(code, name, new_credits, new_bool);
    }


    public static void addCourse(Course course) {
        courses.put(course.getCode(), course);
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_COURSE, AuditUtilClass.AUX);

    }
    public static void addCourse(String code, Subject subject, Professor professor, int year, Year collegeYear,
                                 Semester semester, StudentSeries series) {
        addCourse(new Course(code, subject, professor, year, collegeYear, semester, series));
    }
    public static void addCourse(String code, String subject_id, String professor_cnp, String year, String collegeYear,
                                 String semester, String series_id) {
        Subject subject = subjects.get(subject_id);
        Professor professor = professors.get(Long.parseLong(professor_cnp));
        int new_year = Integer.parseInt(year);
        Year new_collegeYear = Year.valueOf(collegeYear);
        Semester new_semester = Semester.valueOf(semester);
        StudentSeries studentSeries = series.get(series_id);
        addCourse(code, subject, professor, new_year, new_collegeYear, new_semester, studentSeries);
    }
    public static void addGrade(Grade grade) {
        Attendance attendance = new Attendance(grade.getStudent(), grade.getCourse());
        grade.getStudent().addGrade(grade);
        grades.put(attendance, grade);
        attendances.add(attendance);
        AuditableUtil.auditUtil(AuditOption.AUX_ADD_GRADE, AuditUtilClass.AUX);
    }
    public static void addGrade(Student student, Course course, float score) {
        addGrade(new Grade(student, course, score));
    }
    public static void addGrade(String student_cnp, String course_id, String score) {
        Student student = students.get(Long.parseLong(student_cnp));
        Course course = courses.get(course_id);
        float new_score = Float.parseFloat(score);
        addGrade(student, course, new_score);
    }
    public static Student searchStudentByName(String first_name, String surname) {
        for(Student s : students.values()) {
            if(first_name.equalsIgnoreCase(s.getFirst_name())
            && surname.equalsIgnoreCase(s.getSurname())) {
                AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_STUDENT, AuditUtilClass.AUX);
                return s;
            }
        }
        return null;
    }
    public static Student searchStudentByName() {
        String first_name;
        String surname;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first name: ");
        first_name = in.nextLine();
        System.out.println("Enter surname: ");
        surname = in.nextLine();
        Student s = searchStudentByName(first_name, surname);
        if(s == null) {
            System.out.println("Student not found!");
        }
        return s;
    }
    public static void listStudent(){
        Student student = searchStudentByName();
        if(student != null) {
            System.out.println(student);
            if(student.getGroup().getRepresentative() == student) {
                System.out.println("Representative of group " + student.getGroup().getId());
            }
            if(student.getGroup().getSeries().getRepresentative() == student) {
                System.out.println("Representative of series " + student.getGroup().getSeries().getId());
            }
        }
        else {
            System.out.println("Student not found!");
        }
        AuditableUtil.auditUtil(AuditOption.AUX_LIST_STUDENT, AuditUtilClass.AUX);
    }

    public static void setRepresentative(String unitCode) {
        StudentUnit unit;
        unit = series.get(unitCode);
        if(unit == null) unit = groups.get(unitCode);
        if(unit == null) {
            AuditableUtil.auditUtil(AuditOption.AUX_SET_REPRESENTATIVE, AuditUtilClass.AUX);
            System.out.println("Series/Group not found!");
        }
        else {
            Student s = searchStudentByName();
            if(s != null) {
                if(unit == s.getGroup() || unit == s.getGroup().getSeries()) {
                    unit.setRepresentative(s);
                    System.out.println("Representative " + s.getFullName() + " set successfully to " + unit.getId());
                    AuditableUtil.auditUtil(AuditOption.AUX_SET_REPRESENTATIVE, AuditUtilClass.AUX);
                }
                else {
                    System.out.println("Student not in group/series!");
                }
            }
        }
    }
    public static void setRepresentative() {
        String code;
        Scanner in = new Scanner(System.in);
        System.out.println("Series/Group code:");
        code = in.nextLine();
        setRepresentative(code);
    }
    public static Professor searchProfessorByName(String first_name, String surname) {
        for(Professor p : professors.values()) {
            if(first_name.equalsIgnoreCase(p.getFirst_name())
                    && surname.equalsIgnoreCase(p.getSurname())) {
                AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_PROFESSOR, AuditUtilClass.AUX);
                return p;
            }
        }
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_PROFESSOR, AuditUtilClass.AUX);
        return null;
    }
    public static Professor searchProfessorByName() {
        String first_name;
        String surname;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first name: ");
        first_name = in.nextLine();
        System.out.println("Enter surname: ");
        surname = in.nextLine();
        Professor p = searchProfessorByName(first_name, surname);
        if(p == null) {
            System.out.println("Professor not found!");
        }
        return p;
    }

    public static Subject searchSubjectByCode(String subjectCode) {
        Subject s = subjects.get(subjectCode);
        if(s == null) {
            System.out.println("Subject " + subjectCode + " not found!");
        }
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_SUBJECT, AuditUtilClass.AUX);
        return s;
    }

    public static Subject searchSubjectByCode() {
        System.out.println("Subject code: ");
        Scanner in = new Scanner(System.in);
        String subjectCode = in.nextLine();
        return searchSubjectByCode(subjectCode);
    }

    public static Specialization searchSpecializationByCode(String specializationCode) {
        Specialization s = specializations.get(specializationCode);
        if(s == null) {
            System.out.println("Specialization " + specializationCode + " not found!");
        }
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_SPECIALIZATION, AuditUtilClass.AUX);
        return s;
    }
    public static Specialization searchSpecializationByCode() {
        System.out.println("Specialization code: ");
        Scanner in = new Scanner(System.in);
        String specializationCode = in.nextLine();
        return searchSpecializationByCode(specializationCode);
    }
    public static void listStudentsInUnit(StudentUnit u) {
        if(u.getStudents().isEmpty()) {
            StringBuilder message = new StringBuilder();
            if(u.getClass().getSimpleName().equals("StudentGroup")) {
                message.append("Group ");
            }
            else if(u.getClass().getSimpleName().equals("StudentSeries")) {
                message.append("Series ");
            }
            message.append(u.getId()).append(" does not contain any students!");
            System.out.println(message);
        }
        else {
            System.out.println("Listing students:");
            if(u.getClass().getSimpleName().equals("StudentGroup")) {
                for(Student s : u.getStudents()) {
                    System.out.println("\t" + s.getFullName());
                }
            }
            else if(u.getClass().getSimpleName().equals("StudentSeries")) {
                for (Student s : u.getStudents()) {
                    System.out.println("\t" + s.getFullName() + ", " + s.getGroup().getNumber());
                }
            }
            if(u.getRepresentative() != null) {
                System.out.println("Representative: " + u.getRepresentative().getFullName());
            }
        }
        AuditableUtil.auditUtil(AuditOption.AUX_LIST_STUDENTS_IN_UNIT, AuditUtilClass.AUX);
    }
    public static void listStudentsInUnit(String code)
    {
        StudentUnit u;
        u = AuxService.getSeries().get(code);
        if(u == null) u = AuxService.getGroups().get(code);
        if(u == null) {
            System.out.println("Group/series not found!");
        }
        else {
            listStudentsInUnit(u);
        }
    }
    public static void listStudentsInUnit() {
        Scanner in = new Scanner(System.in);
        System.out.println("Series/Group code:");
        String code;
        code = in.nextLine();
        listStudentsInUnit(code);
    }
    public static Course searchCourseByCode() {
        System.out.println("Course Code: ");
        Scanner in = new Scanner(System.in);
        String code = in.nextLine();
        Course c = courses.get(code);
        if(c == null) {
            System.out.println("Course " + code + " not found!");
        }
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_COURSE, AuditUtilClass.AUX);
        return c;
    }
    public static Year searchYear(String yearString) {
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_YEAR, AuditUtilClass.AUX);
        return Year.valueOf(yearString);
    }
    public static Year searchYear() {
        System.out.println("College year (B1-4,M1-2,P1-2): ");
        Scanner in = new Scanner(System.in);
        String year = in.nextLine();
        return searchYear(year);
    }
    public static StudentSeries searchSeriesByCode() {
        System.out.println("Series: ");
        Scanner in = new Scanner(System.in);
        String code = in.nextLine();
        StudentSeries s = series.get(code);
        if(s == null) {
            System.out.println("Series not found!");
        }
        AuditableUtil.auditUtil(AuditOption.AUX_SEARCH_SERIES, AuditUtilClass.AUX);
        return s;
    }
    public static void validateCNP(long cnp) throws CNP_Exception {
        //Format:
        // SAALLZZJJNNNC
        //Check for length
        String cnpString = String.valueOf(cnp);
        if(cnpString.length() != 13)
        {
            //Exception incorrect length
            throw new CNP_Exception("CNP " + cnp + ": Incorrect length (must be 13 digits)");
        }
        else {
            int LL = Integer.parseInt(cnpString.substring(3, 5));
            int S = Integer.parseInt(String.valueOf(cnpString.charAt(0)));
            if(S > 8) {
                //Exception CNP S from 1-8
                throw new CNP_Exception("CNP " + cnp + ": Incorrect S digit (" + S + ": must be 1-8)");
            }
            else {
                if(LL == 0 || LL > 12){
                    //Exception digits LL of CNP don't represent a month
                    throw new CNP_Exception("CNP " + cnp + ": Incorrect LL digits (" + LL + ": must represent a month 1-12)");
                }
                else {
                    int ZZ = Integer.parseInt(cnpString.substring(5, 7));
                    if(ZZ == 0) {
                        //throw exception day not in month
                        throw new CNP_Exception("CNP " + cnp + ": Incorrect ZZ digits (" + ZZ + ": day not in month " + LL + ")");
                    }
                    switch (LL) {
                        case 2 -> {
                            if(ZZ > 28) {
                                // Exception day not in month
                                throw new CNP_Exception("CNP " + cnp + ": Incorrect ZZ digits (" + ZZ + ": day not in month " + LL + ")");
                            }
                        }
                        case 4,6,9,11 -> {
                            if(ZZ > 30) {
                                // Exception day not in month
                                throw new CNP_Exception("CNP " + cnp + ": Incorrect ZZ digits (" + ZZ + ": day not in month " + LL + ")");
                            }
                        }
                        case 1,3,5,7,8,10,12 -> {
                            if(ZZ > 31) {
                                // Exception day not in month
                                throw new CNP_Exception("CNP " + cnp + ": Incorrect ZZ digits (" + ZZ + ": day not in month " + LL + ")");
                            }
                        }
                    }
                    int JJ = Integer.parseInt(cnpString.substring(7, 9));
                    if(JJ == 0 || JJ == 49 || JJ == 50 || JJ > 52) {
                        //Exception invalid county
                        throw new CNP_Exception("CNP " + cnp + ": Invalid county: " + JJ + " Must be between 1-52, excluding 49 and 50");
                    }
                }
            }
        }
    }

    public static void validateScore(float score) throws InvalidGradeException {
        if(score < 1 || score > 10) {
            throw new InvalidGradeException("Score " + score + " not between 1 and 10!");
        }
    }
}
