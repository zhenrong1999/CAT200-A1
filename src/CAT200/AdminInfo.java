package CAT200;

import javafx.beans.property.SimpleStringProperty;

public class AdminInfo {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }
    public String getPassword() {
        return password.get();
    }
    public AdminInfo(String a, String b){
        setData(a, b);
    }
    public void setData(String name, String password){
        this.name.set(name);
        this.password.set(password);
    }
    public void print(){
        System.out.println(name.get() + " " + password.get());
    }
    public boolean checkValid(String a, String b) {
        if (this.name.get() == a && this.password.get()==b)
            return true;
        else
            return false;
    }
}
