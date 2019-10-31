package CAT200;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//the main window
public class MainWindowController implements Initializable {
    static Student_Database student_database = new Student_Database();     //package-private
    private LoginWindowControl loginWindowControl;
    //For Side Menu
    @FXML
    private Button Home, Add, Search, Exit, Save_btn, Settings;
    @FXML
    private Label Title, changeStatus, createStatus;
    @FXML
    private Pane search_pane, add_pane, home_pane, settings_pane, change_pane, register_pane;
    //For Add/Edit Student Info Scene
    @FXML
    private Button submit;
    @FXML
    private TextField cubic_id, name, matricNum, supervisor;
    @FXML
    private DatePicker checkdate;
    @FXML
    private Button changeAdmin, registerAdmin, confirmBtn, createBtn;
    @FXML
    private TextField changeUser, newUser;
    @FXML
    private PasswordField changePwd, newPassword;

    //For Search Student Info Scene
    @FXML
    private TextField search_text;
    @FXML
    private Button search_button;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private AnchorPane table_to_display, side_menu, small_side_menu;

    public void setMainApp(LoginWindowControl control) {
        this.loginWindowControl = control;
    }

    @FXML  //Change from different panes
    private void handlebuttonaction(ActionEvent event) {
        if (event.getSource() == Home) {
            Title.setText("Home");
            home_pane.toFront();
        } else if (event.getSource() == Add) {
            Title.setText("Add Student");
            student_database.resetStudent_namelist();
            add_pane.toFront();
        } else if (event.getSource() == Search) {
            Title.setText("Search Student");
            search_pane.toFront();
        } else if (event.getSource() == changeAdmin) {
            Title.setText("Change Admin Details");
            change_pane.toFront();
        } else if (event.getSource() == registerAdmin) {
            Title.setText("New Admin");
            register_pane.toFront();
        } else if (event.getSource() == Settings) {
            Title.setText("Admin Settings");
            settings_pane.toFront();
        } else if (event.getSource() == Exit) {
            Title.setText("Exiting");
            // get a handle to the stage
            save_to_database();
            Stage stage = (Stage) Exit.getScene().getWindow();
            Main main = new Main();
            try {
                main.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Add Student
    public void userClickSubmit() {
        Student newStud = new Student(matricNum.getText(), name.getText(), cubic_id.getText(), Student_Database.date_converter.toString(checkdate.getValue()), supervisor.getText());
        String error_message = newStud.validation();
        error_message += student_database.validation_matric_no_match_name(newStud);
        error_message += student_database.validation_check_date_with_cubical(newStud);
        //validation returns empty string when there is no error
        if (error_message.equals("")) {
            student_database.add(newStud);
            cubic_id.setText("");
            name.setText("");
            matricNum.setText("");
            checkdate.setValue(null);
            supervisor.setText("");
            //add the new student info into textfile
            loginWindowControl.reader.SaveToFile(student_database);
        } else {
            error_message_box(error_message);
            System.out.println("Error In Student");
        }
    }

    //error message pop out (a new window for error handling)
    void error_message_box(String error_message) {
        try {
            Stage error = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("errorWindow.fxml"));
            Parent root = loader.load();
            ErrorWindowController controller = loader.getController();
            controller.setError_text(error_message);
            error.setTitle("Error");
            Scene scene = new Scene(root, 600, 300);
            error.setScene(scene);
            error.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Search
    //this is used in searching for particular searching information
    public void search_in_action() {
        try {
            FXMLLoader display_table_loader = new FXMLLoader(getClass().getResource("Display_Table.fxml"));
            table_to_display.getChildren().setAll((AnchorPane) display_table_loader.load());
            DisplayTableControl display_table_controller = display_table_loader.getController();
            display_table_controller.setMainWindowController(this);
            display_table_controller.pane_table.setPrefSize(452, 200);
            display_table_controller.table_of_students.setPrefSize(452, 150);
            display_table_controller.SetToDisplay(type.getValue(), search_text.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This is used to display all student information
    public void DisplayTable() throws IOException {
        Stage stage = new Stage();
        FXMLLoader display_table_loader = new FXMLLoader(getClass().getResource("Display_Table.fxml"));
        Parent root = display_table_loader.load();
        DisplayTableControl display_table_controller = display_table_loader.getController();
        display_table_controller.setMainWindowController(this);
        display_table_controller.SetToDisplay("Display All", "");
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Display All Student Information");
        stage.setScene(scene);
        stage.show();
    }

    //this is used in advanced searching
    public void DisplayAdvancedSearch() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Advanced_Search.fxml"));
        Parent root = loader.load();
        AdvanceSearchControl control = loader.getController();
        control.setMainWindowControllerController(this);
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Advanced Search");
        stage.setScene(scene);
        stage.show();
    }

    //confirm the changes for the username and password
    //get username and pwd from textfield
    public void userConfirmsChange(ActionEvent event) {
        for (int i = 0; i < loginWindowControl.adminList.size(); i++) {
            //Delete ori from linkedlist, the insert a new one
            if (loginWindowControl.user.equals(loginWindowControl.adminList.get(i).getName())) {
                loginWindowControl.adminList.remove(i);
                AdminInfo newAdmin = new AdminInfo(changeUser.getText(), changePwd.getText());
                loginWindowControl.user = changeUser.getText();
                loginWindowControl.pwd = changePwd.getText();
                loginWindowControl.adminList.add(newAdmin);
                changeStatus.setText("Change Successful");
                changeStatus.setVisible(true);
            }
        }
        //save modified admin info into textfile
        loginWindowControl.file_handling.SaveToFile(loginWindowControl.adminList);
        loginWindowControl.printLinkedList();
    }

    //Create new admin
    //At settings->register new admin->enter your name and password->click submit
    public void registerNewAdmin(ActionEvent event) {
        AdminInfo newAdmin = new AdminInfo(newUser.getText(), newPassword.getText());
        boolean found = false;
        for (int i = 0; i < loginWindowControl.adminList.size(); i++)
            if (loginWindowControl.adminList.get(i).getName().equals(newUser.getText()))
                found = true;

        if (found) { //if admin info exists
            createStatus.setText("Admin exists. Please try again");
            createStatus.setVisible(true);
        } else {
            createStatus.setText("New Admin creation successful!!! Welcome " + newUser.getText());
            createStatus.setVisible(true);
            loginWindowControl.adminList.add(newAdmin);  //adds new admin to linkedlist
        }
        //save new admin info to textfile
        loginWindowControl.file_handling.SaveToFile(loginWindowControl.adminList);
        loginWindowControl.printLinkedList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] typelist = {"Matric number", "Name Student", "Cubicle ID", "Check in date", "Supervisor"};
        type.setItems(FXCollections.observableArrayList(typelist));
        changeStatus.setVisible(false);
        createStatus.setVisible(false);
        home_pane.toFront();
        checkdate.setConverter(Student_Database.date_converter);

        side_menu_exited();

    }

    //a drop-right box at the left side of the app
    //when mouse scroll, the box expands
    public void side_menu_entered() {
        side_menu.setPrefWidth(200);
        //side_menu.setMaxWidth(200);
        Add.setPrefWidth(200);
        Search.setPrefWidth(200);
        Exit.setPrefWidth(200);
        Settings.setPrefWidth(200);
        Save_btn.setPrefWidth(200);
        Home.setPrefWidth(200);
    }

    public void side_menu_exited() {
        //side_menu.setMinWidth(45);
        side_menu.setPrefWidth(45);
        //side_menu.setMaxWidth(45);

        Add.setPrefWidth(45);
        Search.setPrefWidth(45);
        Exit.setPrefWidth(45);
        Settings.setPrefWidth(45);
        Save_btn.setPrefWidth(45);
        Home.setPrefWidth(45);
    }

    //Save to  text file
    public void save_to_database() {
        String error_message = student_database.reValidation();
        if (error_message.equals(""))
            loginWindowControl.reader.SaveToFile(student_database);
        else {
            error_message_box(error_message);
        }
    }

}
