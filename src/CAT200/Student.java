package CAT200;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public boolean validation() {
        Boolean valid = true;
        try {
            //Validate the cubical id, 1-3 for first char, A-M for 2nd char, 1-6 for 3rd char
            if (cubic_id.get().length() != 3 || matric_no.get().length() != 6)
                valid = false;
            int lab_id = Character.getNumericValue(cubic_id.get().charAt(0));
            //convert char to int
            char row = cubic_id.get().charAt(1);
            int column = Character.getNumericValue(cubic_id.get().charAt(2));
            if (lab_id > 3 || lab_id < 1 || ((int) row) < 65 || ((int) row) > 77 || column < 1 || column > 6)
                valid = false;
            int a = Integer.parseInt(matric_no.get());
            Date d1 = new SimpleDateFormat("dd-MM-yyyy").parse(checkdate.get());
        } catch (/*NumberFormatException | ParseException | */Exception e) {
            valid = false;

            Stage error = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("errorWindow.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            error.setTitle("Error");
            Scene scene = new Scene(root, 600, 400);
            error.setScene(scene);
            error.show();

            System.out.println("Error In Student");
        }
        return valid;
    }
}
