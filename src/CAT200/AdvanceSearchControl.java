package CAT200;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvanceSearchControl implements Initializable {
    public DatePicker start_date, end_date;
    @FXML
    private Button search_button;
    @FXML
    private TextField matric_text, name_text, cubic_id_text, supervisor_text;
    @FXML
    private CheckBox check_matric, check_name, check_cubicle_id, check_check_date, check_supervisor;

    private MainWindowController mainWindowController;

    void setMainWindowControllerController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        start_date.setConverter(Student_Database.date_converter);
        end_date.setConverter(Student_Database.date_converter);
    }

    public void after_key_in() {
        if (end_date.getValue() == null) {
            end_date.setValue(start_date.getValue());
        } else if (end_date.getValue().compareTo(start_date.getValue()) <= 0)
            end_date.setValue(start_date.getValue());
    }

    public void search_action() throws IOException {
        boolean[] check_type = new boolean[5];
        check_type[0] = check_matric.isSelected();
        check_type[1] = check_name.isSelected();
        check_type[2] = check_cubicle_id.isSelected();
        check_type[3] = check_check_date.isSelected();
        check_type[4] = check_supervisor.isSelected();
        String search_item = "";
        if (check_type[0]) {
            search_item += matric_text.getText();
        }
        search_item += ",";
        if (check_type[1]) {
            search_item += name_text.getText();
        }
        search_item += ",";
        if (check_type[2]) {
            search_item += cubic_id_text.getText();
        }
        search_item += ",";
        if (check_type[3]) {
            search_item += Student_Database.date_converter.toString(start_date.getValue()) + ",";
            search_item += Student_Database.date_converter.toString(end_date.getValue());
        }
        search_item += ",";
        if (check_type[4]) {
            search_item += supervisor_text.getText();
        }
        search_item += ",";
        if (!search_item.equals(",,,,,,")) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Display_Table.fxml"));
            Parent root = loader.load();
            DisplayTableControl control = loader.getController();
            control.SetToDisplay("ALL",search_item);
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Display All Student Information");
            stage.setScene(scene);
            stage.show();
        }
    }
}