package CAT200;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.LinkedList;

public class Student_Database extends LinkedList<Student> {


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

    public int search(String type, String search_item) {
        sort(type);
        int first = 0;
        int current_index;
        int last = size() - 1;
        String temp;
        boolean finding = false;
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
                current_index=-1;
                break;
            }
            System.out.println(current_index);
        } while (!finding);
        return current_index;
    }

    public Student_Database searchForAll(String type, String search_item) {
        sort(type);
        String temp;
        Student_Database result=new Student_Database();
        boolean found=false;
        for(Student current_student: this)
        {
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
            if(temp.equals(search_item)){
                result.add(current_student);
                found=true;
            }
            if(!temp.equals(search_item)&&found){
                break;
            }
        }
        return result;
    }

    public void addFromLinkedList(LinkedList<String> raw_data) {
        String[] data_extracted;
        for (int i = 0; i < raw_data.size(); i++) {
            data_extracted = raw_data.get(i).split(" ");
            try {
                super.add(i, new Student(data_extracted[0],data_extracted[1],data_extracted[2],data_extracted[3],data_extracted[4]));
            } catch (Exception e) {
                throw new IllegalStateException("There are alphabet in line " + i);
            }
        }
    }
}
