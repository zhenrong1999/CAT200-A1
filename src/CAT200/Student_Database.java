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
    private LinkedList<Student> student_namelist = new LinkedList<Student>();

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

    //not using
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
        int x = 0;
        for (int i = 0; i < raw_data.size(); i++) {
            data_extracted = raw_data.get(i).split(" ");
            try {
                Student newStud = new Student(data_extracted[0], data_extracted[1], data_extracted[2], data_extracted[3], data_extracted[4]);
                valid = newStud.validation();
                valid += validation_matric_no_match_name(newStud);
                valid += validation_check_date_with_cubical(newStud);
                if (valid.equals("")) {
                    super.add(x, newStud);
                    x++;
                } else {
                    System.out.println("There are error in line " + i);
                    System.out.println(valid);
                    error++;
                }
            } catch (Exception e) {
                throw new IllegalStateException("There are error in line " + i);
            }
        }
        System.out.println("No. of errors : " + error);
    }

    //check whether the matric number is same with the name
    public String validation_matric_no_match_name(Student student) {
        if (!isEmpty()) {
            for (Student record_stu : student_namelist) {
                if (record_stu.getMatric_no().equals(student.getMatric_no())) {
                    if (record_stu.getName().equals(student.getName())) {
                        return "";
                    } else {
                        return "Same Matric Number has found with different name.\n";
                    }
                }
            }
        }
        student_namelist.add(new Student(student.getMatric_no(), student.getName()));
        return "";
    }

    //check if the date and place has been used/book
    public String validation_check_date_with_cubical(Student student) {
        sort("Check in date");
        if (!isEmpty()) {
            for (Student record_stu : this) {
                if (record_stu.getCheckdate().equals(student.getCheckdate())) {
                    if (record_stu.getCubic_id().equals(student.getCubic_id())) {
                        if (record_stu.getMatric_no().equals(student.getMatric_no())) {
                            if (record_stu.getName().equals(student.getName())) {
                                return "Repeated record with same student, place and date.\n";
                            }
                        } else
                            return "The cubical has been used by other student.\n";
                    }
                }
            }
        }
        return "";
    }

//not using
    public void edit_all(String mode, Student student) {
        sort("Matric number ");

        for (Student namelist_stu : student_namelist) {
            if (namelist_stu.getMatric_no().equals(student.getMatric_no())) {
                namelist_stu.setName(student.getName());
                break;
            }
        }

        for (int i = 0; i < size(); i++) {
            if (get(i).getMatric_no().equals(student.getMatric_no())) {
                while (get(i).getMatric_no().equals(student.getMatric_no())) {
                    {
                        get(i).setName(student.getName());
                        i++;
                    }
                }
                return;
            }
        }
    }

    //reset the student namelist
    public void resetStudent_namelist() {
        student_namelist = new LinkedList<>();
        sort("Matric number");
        student_namelist.add(new Student(getFirst().getMatric_no(), getFirst().getName()));
        for (Student student : this) {
                if (!student.getMatric_no().equals(student_namelist.getLast().getMatric_no()))
                    student_namelist.add(new Student(student.getMatric_no(), student.getName()));
        }
    }

    public String reValidation(){
        Student_Database temp=new Student_Database();
        temp.add(getFirst());
        int i=1;
        String Error_message="";
        String error="";
        while(i<size()){
            error=temp.validation_matric_no_match_name(get(i));
            error+=temp.validation_check_date_with_cubical(get(i));
            if(!error.equals(""))
            {
                Error_message+="Error in"+get(i).print();
                Error_message+=error;
            }
        }
        return Error_message;
    }

//copy student namelist from other Student_Database
    public void setStudent_namelist(LinkedList<Student> student_namelist) {
        for (Student student : student_namelist) {
            this.student_namelist.add(student.clone());
        }
    }

    //clone the whole student database
    public Student_Database deep_clone() {
        Student_Database clone = new Student_Database();
        for (Student student : this) {
            clone.add(student.clone());
        }
        clone.setStudent_namelist(student_namelist);
        return clone;
    }

}
