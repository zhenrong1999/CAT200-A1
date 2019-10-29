package CAT200;

import javafx.beans.property.SimpleStringProperty;

public class AdminInfo {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();

    public AdminInfo(String a, String b) {
        setData(a, b);
    }

    public String getName() {
        return name.get();
    }

    public String getPassword() {
        return password.get();
    }

    public void setData(String name, String password) {
        this.name.set(name);
        this.password.set(password);
    }

    public void print() {
        System.out.println(name.get() + " " + password.get());
    }

    public boolean checkValid(String a, String b) {
        return this.name.get() == a && this.password.get() == b;
    }
}
