package CAT200;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    Student_Database student_database = new Student_Database();     //package-private
    private Main main;
    //For Side Menu
    @FXML
    private Button Home, Add, Search, Exit;
    @FXML
    private Label Title;
    @FXML
    private Pane search_pane, add_pane, home_pane;
    //For Add/Edit Student Info Scene
    @FXML
    private Button submit;
    @FXML
    private TextField cubic_id, name, matricNum, checkdate, supervisor;


    //For Search Student Info Scene
    @FXML
    private TextField search_text;
    @FXML
    private Button search_button;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private AnchorPane table_to_display;

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
    }

    @FXML
    private void handlebuttonaction(ActionEvent event) {
        if (event.getSource() == Home) {
            Title.setText("Home");
            home_pane.toFront();
        } else if (event.getSource() == Add) {
            Title.setText("Add Student");
            add_pane.toFront();
        } else if (event.getSource() == Search) {
            Title.setText("Search Student");
            search_pane.toFront();
        } else if (event.getSource() == Exit) {
            Title.setText("Exiting");
            // get a handle to the stage
            main.reader.SaveToFile(student_database);
            Stage stage = (Stage) Exit.getScene().getWindow();
            stage.close();
        }
    }

    public void userClickSubmit() throws IOException {
        Student newStud = new Student(cubic_id.getText(), name.getText(), matricNum.getText(), checkdate.getText(), supervisor.getText());
        cubic_id.setText("");
        name.setText("");
        matricNum.setText("");
        checkdate.setText("");
        supervisor.setText("");
        if (newStud.validation()) {
            student_database.add(newStud);
            main.reader.SaveToFile(student_database);
        }
    }

    public void search_in_action() {
        Student_Database result = student_database.searchForAll(type.getValue(), search_text.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Display_Table.fxml"));
        try {
            table_to_display.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DisplayTableControl control = loader.getController();
        control.pane_table.setPrefSize(452, 200);
        control.tableofstudents.setPrefSize(452, 150);
        control.label_list_of_student.setLayoutX(150);
        control.SetToDisplay(result);
    }


    public void DisplayTable() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Display_Table.fxml"));
        Parent root = loader.load();
        DisplayTableControl control = loader.getController();
        control.SetToDisplay(student_database);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Display All Student Infomtion");
        stage.setScene(scene);
        stage.show();
    }

    public void DisplayAdvancedSearch() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Advanced_Search.fxml"));
        Parent root = loader.load();
        AdvanceSearchControl control = loader.getController();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Display All Student Infomtion");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] typelist = {"Cubicle ID", "Name Student", "Matric number", "Check in date", "Supervisor"};
        type.setItems(FXCollections.observableArrayList(typelist));
        home_pane.toFront();
    }
}
