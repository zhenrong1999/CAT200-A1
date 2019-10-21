package CAT200;

import javafx.beans.property.SimpleStringProperty;

public class Student {

    private SimpleStringProperty cubic_id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty matric_no = new SimpleStringProperty();
    private SimpleStringProperty checkdate = new SimpleStringProperty();
    private SimpleStringProperty supervisor = new SimpleStringProperty();

    public Student(String name) {
        this.name.set(name);
    }

    public Student(String matricno, String name, String cubic_id, String checkdate, String supervisor) {
        setData(matricno, name, cubic_id, checkdate, supervisor);
    }

    public String getCheckdate() {
        return checkdate.get();
    }

    public String getCubic_id() {
        return cubic_id.get();
    }

    public String getMatric_no() {
        return matric_no.get();
    }

    public String getName() {
        return name.get();
    }

    public String getSupervisor() {
        return supervisor.get();
    }

    public void setData(String matricno, String name, String cubic_id, String checkdate, String supervisor) {
        this.matric_no.set(matricno);
        this.name.set(name);
        this.cubic_id.set(cubic_id);
        this.checkdate.set(checkdate);
        this.supervisor.set(supervisor);
    }

    public void print() {
        System.out.println(matric_no.get() + " " + name.get() + " " + cubic_id.get() + " " + checkdate.get() + " " + supervisor.get());
    }
}
