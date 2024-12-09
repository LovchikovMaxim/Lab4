import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.util.*;

public class InfoList {
    private class Person {
       private String id;
       private String name;
       private String gender;
       private String birthdate;
       private Division division;
       private String salary;

       Person(String id, String name, String gender, String birthdate, Division division, String salary) {
           this.id = id;
           this.name = name;
           this.gender = gender;
           this.birthdate = birthdate;
           this.division = division;
           this.salary = salary;
       }
    }

    private class Division {
        private int id;
        private String name;

        Division(String name) {
            this.name = name;
            Random random = new Random();
            this.id = random.nextInt(100);
        }

        Division(Division other) {
            this.name = other.name;
            this.id = other.id;
        }
    }

    private final ArrayList<Person> personList = new ArrayList<>();

    public void readCsvFile(String csvFilePath) throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(csvFilePath);
             InputStreamReader inputStreamReader = new InputStreamReader(in)) {

            if (in == null) {
                throw new FileNotFoundException(csvFilePath);
            }

            CSVReader reader = new CSVReader(inputStreamReader);
            String[] nextLine;
            boolean isFirstLine = true;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 6) {
                    continue;
                }

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String id = nextLine[0];
                String name = nextLine[1];
                String gender = nextLine[2];
                String birthdate = nextLine[3];
                String divisionName = nextLine[4];
                String salary = nextLine[5];

                Division division = new Division(divisionName);
                Person person = new Person(id, name, gender, birthdate, division, salary);

                personList.add(person);
            }
        }
    }

    public void printPersons() {
        for (Person person : personList) {
            System.out.println("ID: " + person.id +
                    ", Name: " + person.name +
                    ", Gender: " + person.gender +
                    ", Birthdate: " + person.birthdate +
                    ", Division: " + person.division.name +
                    ", Salary: " + person.salary);
        }
    }

    public static void main(String[] args) throws Exception {
        InfoList infoList = new InfoList();
        infoList.readCsvFile("foreign_names.csv");
        infoList.printPersons();
    }
}
