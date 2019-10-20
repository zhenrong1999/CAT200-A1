package CAT200;

import java.util.Comparator;
import java.util.LinkedList;


public class student_database extends LinkedList<Student> {
    public Student search(String type, String search_item) {
        Student student_found = new Student("no found");
        switch (type) {
            case "Name Student":
                sort(Comparator.comparing(Student::getName));
                break;
            case "Cubicle ID":
                sort(Comparator.comparing(Student::getCubic_id));
                break;
            case "Matric number":
                sort(Comparator.comparing(Student::getMatricNum));
                break;
            case "Check in date":
                sort(Comparator.comparing(Student::getCheckdate));
                break;
            case "Supervisor":
                sort(Comparator.comparing(Student::getSupervisor));
                break;
        }
        int first = 0;
        int half;
        int last = size() - 1;
        String temp;
        boolean finding = false;
        do {
            half = (first + last) / 2;

            switch (type) {
                case "Name Student":
                    temp = get(half).getName();
                    break;
                case "Cubicle ID":
                    temp = get(half).getCubic_id();
                    break;
                case "Matric number":
                    temp = get(half).getMatricNum();
                    break;
                case "Check in date":
                    temp = get(half).getCheckdate();
                    break;
                case "Supervisor":
                    temp = get(half).getSupervisor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }

            if (temp.equals(search_item)) {
                student_found = get(half);
                finding = true;
            } else if (temp.compareTo(search_item) > 0) {
                last = half - 1;
            } else if (temp.compareTo(search_item) < 0) {
                first = half + 1;
            }
            if (first == half || last == half) {
                break;
            }
            System.out.println(half);
        } while (!finding);

        return student_found;
    }
}
