package CAT200;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

//This is a class that acts like a database to store all booking information
public class Student_Database extends LinkedList<Student> {

    public static String pattern = "dd-MM-yyyy";
    public static StringConverter<LocalDate> date_converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        @Override //Convert local date format to pattern format
        public String toString(LocalDate localDate) {
            if (localDate != null) {
                return dateFormatter.format(localDate);
            } else {
                return "";
            }
        }

        @Override  //convert pattern format to local date format
        public LocalDate fromString(String s) {
            if (s != null && !s.isEmpty()) {
                return LocalDate.parse(s, dateFormatter);
            } else {
                return null;
            }
        }
    };

    //sort the database based on the type of information(i.e. student name)
    public void sort(String type) {
        switch (type) {
            case "Name Student":
                sort(Comparator.comparing(Student::getName));
                break;
            case "Cubicle ID":
                sort(Comparator.comparing(Student::getCubic_id));
                break;
            case "Matric number":
                sort(Comparator.comparing(Student::getMatric_no));
                break;
            case "Check in date":
                sort(Comparator.comparing(Student::getCheckdate));
                break;
            case "Supervisor":
                sort(Comparator.comparing(Student::getSupervisor));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    //search for relevant data information
    public int search(String type, String search_item) {
        sort(type);
        int first = 0;
        int current_index;
        int last = size() - 1;
        String temp;
        boolean finding = false;
        //binary searching for the correct information
        do {
            current_index = (first + last) / 2;

            switch (type) {
                case "Name Student":
                    temp = get(current_index).getName();
                    break;
                case "Cubicle ID":
                    temp = get(current_index).getCubic_id();
                    break;
                case "Matric number":
                    temp = get(current_index).getMatric_no();
                    break;
                case "Check in date":
                    temp = get(current_index).getCheckdate();
                    break;
                case "Supervisor":
                    temp = get(current_index).getSupervisor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }

            if (temp.equals(search_item)) {
                finding = true;
            } else if (temp.compareTo(search_item) > 0) {
                last = current_index - 1;
            } else if (temp.compareTo(search_item) < 0) {
                first = current_index + 1;
            }
            if (first == current_index || last == current_index) {
                current_index = -1;
                break;
            }
            System.out.println(current_index);
        } while (!finding);
        return current_index;
        //returns the index of the booking information in the database
    }

    //searches for all related information in database and put into a new
    //database
    public Student_Database searchForAll(String type, String search_item) {
        //sort the data with the indicated type of info, then only retrieve
        sort(type);
        String temp;
        Student_Database result = new Student_Database();
        boolean found = false;
        //search through the linkedlist
        for (Student current_student : this) {
            switch (type) {
                case "Name Student":
                    temp = current_student.getName();
                    break;
                case "Cubicle ID":
                    temp = current_student.getCubic_id();
                    break;
                case "Matric number":
                    temp = current_student.getMatric_no();
                    break;
                case "Check in date":
                    temp = current_student.getCheckdate();
                    break;
                case "Supervisor":
                    temp = current_student.getSupervisor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            if (temp.equals(search_item)) {
                result.add(current_student);
                found = true;
            }
            if (!temp.equals(search_item) && found) {
                break;
            }
        }
        return result;
    }

    //search the database with multiple parameters(i.e. student name and matric)
    //advanced searching
    public Student_Database searchForAllwithMultiple(String search_item) {
        String[] temp = search_item.split(",");
        Student_Database result = (Student_Database) this.clone();
        int max_index = temp.length - 1;
        System.out.println(max_index);
        String max_date = "", min_date;
        while (max_index >= 0) {
            switch (max_index) {
                case 5:
                    if (!temp[max_index].isEmpty()) {
                        result = result.searchForAll("Supervisor", temp[max_index]);
                    }
                    max_index--;
                case 4:
                    if (!temp[max_index].isEmpty()) {
                        max_date = temp[max_index];
                    }
                    max_index--;
                case 3:
                    if (!temp[max_index].isEmpty()) {
                        min_date = temp[max_index];
                        if (!max_date.isEmpty())
                            if (max_date.equals(min_date))
                                result = result.searchForAll("Check in date", temp[max_index]);
                            else {
                                LocalDate start = date_converter.fromString(min_date);
                                ArrayList<Student_Database> temp_sub_database = new ArrayList<>();
                                while (!max_date.equals(date_converter.toString(start))) {
                                    temp_sub_database.add(result.searchForAll("Check in date", date_converter.toString(start)));
                                    start = start.plusDays(1);
                                }
                                Student_Database temp_all_stu_database = new Student_Database();
                                for (Student_Database student_database : temp_sub_database) {
                                    temp_all_stu_database.addAll(student_database);
                                }
                                System.out.println(temp_all_stu_database.size());
                                result = temp_all_stu_database;
                            }
                    }
                    max_index--;
                case 2:
                    if (!temp[max_index].isEmpty()) {
                        result = result.searchForAll("Cubicle ID", temp[max_index]);
                    }
                    max_index--;
                case 1:
                    if (!temp[max_index].isEmpty()) {
                        result = result.searchForAll("Name Student", temp[max_index]);
                    }
                    max_index--;
                case 0:
                    if (!temp[max_index].isEmpty()) {
                        result = result.searchForAll("Matric number", temp[max_index]);
                    }
                    max_index--;
                    break;
            }
            max_index--;
        }
        return result;
    }

    //adding new student data into the linkedlist
    public void addFromLinkedList(LinkedList<String> raw_data) {
        String[] data_extracted;
        String valid;
        int error = 0;
        int x=0;
        for (int i = 0; i < raw_data.size(); i++) {
            data_extracted = raw_data.get(i).split(" ");
            try {
                Student newStud = new Student(data_extracted[0], data_extracted[1], data_extracted[2], data_extracted[3], data_extracted[4]);
                valid = newStud.validation();
                System.out.println(valid);
                if (valid.equals("")) {
                    super.add(x, newStud);
                    x++;
                }
                else
                    error++;
            } catch (Exception e) {
                throw new IllegalStateException("There are error in line " + i);
            }
        }
        System.out.println("No. of errors : " + error);
    }

    //clone the whole student database
    public Student_Database deep_clone() {
        Student_Database clone = new Student_Database();
        for (Student student : this) {
            clone.add(student.clone());
        }

        return clone;
    }

}
