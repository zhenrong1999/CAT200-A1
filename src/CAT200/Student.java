package CAT200;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {

    private SimpleStringProperty cubic_id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty matric_no = new SimpleStringProperty();
    private SimpleStringProperty checkdate = new SimpleStringProperty();
    private SimpleStringProperty supervisor = new SimpleStringProperty();
    private CheckBox checkbox=new CheckBox();

    public Student(String name) {
        this.name.set(name);
    }

    public Student(String matric_no, String name, String cubic_id, String checkdate, String supervisor) {
        setData(matric_no, name, cubic_id, checkdate, supervisor);
    }

    public String getCheckdate() {
        return checkdate.get();
    }

    public void setCheckdate(String checkdate) {
        this.checkdate.set(checkdate);
    }

    public String getCubic_id() {
        return cubic_id.get();
    }

    public void setCubic_id(String cubic_id) {
        this.cubic_id.set(cubic_id);
    }

    public String getMatric_no() {
        return matric_no.get();
    }

    public void setMatric_no(String matric_no) {
        this.matric_no.set(matric_no);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSupervisor() {
        return supervisor.get();
    }

    public void setSupervisor(String supervisor) {
        this.supervisor.set(supervisor);
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public void setData(String matric_no, String name, String cubic_id, String checkdate, String supervisor) {
        this.matric_no.set(matric_no);
        this.name.set(name);
        this.cubic_id.set(cubic_id);
        this.checkdate.set(checkdate);
        this.supervisor.set(supervisor);
    }

    public void print() {
        System.out.println(matric_no.get() + " " + name.get() + " " + cubic_id.get() + " " + checkdate.get() + " " + supervisor.get());
    }

    public String validation() {
        String valid = "";
        try {
            //Validate the matric number
            if (getMatric_no().length() > 0) {
                System.out.println(getMatric_no());
                System.out.println(getName().length());
                if (matric_no.get().length() != 6)
                    valid += "Matric Number must be 6 digits.\n";
                try {
                    int temp = Integer.parseInt(matric_no.get());
                    if (temp < 0) {
                        valid += "Matric Number cannot be negative number.\n";
                    }
                } catch (NumberFormatException e) {
                    valid += "Matric Number must be integer.\n";
                }
            } else {
                valid += "Matric Number cannot be empty.\n";
            }
            //Validate Name
            if (getName().length() <= 0) {
                valid += "Student name cannot be empty.\n";
            }

            //Validate the cubical id, 1-3 for first char, A-M for 2nd char, 1-6 for 3rd char
            if (getCubic_id().length() > 0) {
                if (cubic_id.get().length() == 3) {
                    try {
                        int lab_id = Character.getNumericValue(cubic_id.get().charAt(0)); //convert  char/string to int
                        if (lab_id > 3 || lab_id < 1)
                            valid += "The first character of Cubical ID must be from 1 to 3.\n";
                    } catch (NumberFormatException e) {
                        valid += "The first character of Cubical ID must be integer.\n";
                    }
                    try {
                        int row = cubic_id.get().charAt(1);
                        if (row < 65 /*A*/ || row > 77 /*M*/)
                            valid += "The second character of Cubical ID must be from A to M.+\n";
                    } catch (NumberFormatException e) {
                        valid += "Invalid Input for second character of Cubical ID\n";
                    }
                    try {
                        int column = Character.getNumericValue(cubic_id.get().charAt(2));
                        if (column < 1 || column > 6)
                            valid += "The third character of Cubical ID must be from 1 to 6.\n";
                    } catch (NumberFormatException e) {
                        valid += "The third character of Cubical ID must be integer.\n";
                    }
                } else {
                    valid += "Cubical ID must be in the length of 3 character.\n";
                }
            } else {
                valid += "Cubical ID cannot be empty\n";
            }
            //Validate Date Format
            if (getCheckdate().length() > 0) {
                try {
                    Date tempdate = new SimpleDateFormat("dd-MM-yyyy").parse(checkdate.get());
                } catch (ParseException e) {
                    valid += "Date format is wrong\n";
                }
            } else {
                valid += "Date cannot be empty\n";
            }

            if (getSupervisor().length() <= 0)
                valid += "Supervisor's Name cannot be empty\n";

        } catch (Exception e) {
            valid += "Failed to add the student\n";
        }
        return valid;
    }

    public  Student clone(){
        return new Student(getMatric_no(), getName(), getCubic_id(), getCheckdate(), getSupervisor());
    }
}
