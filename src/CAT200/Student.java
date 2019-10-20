package CAT200;

public class Student {
    private String cubic_id, name, matricNum, checkdate, supervisor;

    public Student(String a) {
        name = a;
    }
    public Student(String a, String b, String c, String d, String e) {
        cubic_id = a; name = b; matricNum = c; checkdate = d; supervisor = e;
    }

    public String getCheckdate() {
        return checkdate;
    }
    public String getCubic_id() {
        return cubic_id;
    }
    public String getMatricNum() {
        return matricNum;
    }
    public String getName() {
        return name;
    }
    public String getSupervisor() {
        return supervisor;
    }

    public void setData(String a, String b, String c, String d, String e) {
        cubic_id = a; name = b; matricNum = c; checkdate = d; supervisor = e;
    }
    public void print() {
        System.out.println(cubic_id + " " + name + " " + matricNum + " " + checkdate + " " + supervisor);
    }
}
