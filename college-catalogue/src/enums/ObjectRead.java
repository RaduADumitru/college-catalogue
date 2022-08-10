package enums;

public enum ObjectRead {
    SPECIALIZATIONS("data/specializations.csv"),
    SERIES("data/series.csv"),
    GROUPS("data/groups.csv"),
    STUDENTS("data/students.csv"),
    PROFESSORS("data/professors.csv"),
    SUBJECTS("data/subjects.csv"),
    COURSES("data/courses.csv"),
    GRADES("data/grades.csv");
    private final String filepath;

    ObjectRead(String s) {
        this.filepath = s;
    }

    public String getFilepath() {
        return filepath;
    }
}
