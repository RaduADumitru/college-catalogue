package enums;

public enum Semester {
    I("Semester 1", "Sem1"),
    II("Semester 2", "Sem2");
    private final String name;
    private final String short_name;
    Semester(String name, String short_name){
        this.name=name;
        this.short_name=short_name;
    };
}
