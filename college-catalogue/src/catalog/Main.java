package catalog;


import service.CSV_Writer;
import service.Menu;
import service.Service;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        CSV_Writer.getInstance();
        Service.loadData();
//        //1) Adaugare student
//        Service.addStudent();
//        //2) Afisare informatii despre un student
//        Service.listStudent();
//        //3) Setare reprezentant de grupa/serie
//        Service.setRepresentative();
//        //4) Listare studenti in grupa sau serie
//        Service.listStudentsInGroupOrSeries();
//        //5) Adaugare curs predat unui profesor
//        Service.addCourseToProfessor();
//        //6) Adaugare nota unui student
//        Service.addGradeToStudent();
//        //7) Calculare medie anuala a unui student
//        Service.showYearlyAverageOfStudent();
//        //8) Listare cursuri corespunzatoare unei materii
//        Service.listCoursesOfSubject();
//        //9) Afisare nota medie a unei serii
//        Service.showAverageOfSeries();
//        //10) Adaugare specializare unui student
//        Service.addSpecializationToStudent();
//        //Pentru verificare ca au fost inserate informatiile
//        Service.listStudent();
        Menu.runMenu();
        Service.end();
    }
}
