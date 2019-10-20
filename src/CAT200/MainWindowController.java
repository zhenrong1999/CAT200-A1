package CAT200;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    Student_Database student_database = new Student_Database(); //package-private
    private Main main;
    public void setMainApp(Main mainApp) {
        this.main = mainApp;}

    //For Side Menu
    @FXML
    private Button Home,Add,Search,Exit;
    @FXML
    private Label Title;
    @FXML
    private Pane search_pane,add_pane,home_pane;
    @FXML
    private void handlebuttonaction(ActionEvent event){
        if(event.getSource()==Home)
        {Title.setText("Home");
        home_pane.toFront();}
        else if(event.getSource()==Add)
            {Title.setText("Add Student");
            add_pane.toFront();}
        else if (event.getSource()==Search)
            {Title.setText("Search Student");
            search_pane.toFront();}
        else if(event.getSource()==Exit)
            {Title.setText("Exiting");
            // get a handle to the stage
                main.reader.SaveToFile(student_database);
            Stage stage = (Stage) Exit.getScene().getWindow();
            stage.close();}
    }


    //For Add/Edit Student Info Scene
    @FXML
    private Button submit;
    @FXML
    private TextField cubic_id,name,matricNum,checkdate,supervisor;

    public void userClickSubmit(){
        Student newStud = new Student(cubic_id.getText(), name.getText(), matricNum.getText(), checkdate.getText(), supervisor.getText());
        student_database.add(newStud);
    }


    //For Search Student Info Scene
    @FXML
    private TextField search_text;
    @FXML
    private Button search_button;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private TableColumn matric_column, name_column, cubicle_column,    date_column,           superisor_column;
@FXML
private  TableView search_result;
    public void search_in_action() {
        Student_Database result=student_database.searchForAll(type.getValue(),search_text.getText());
        ObservableList<Student> personData = FXCollections.observableArrayList();

        if(result.size()!=0)
            {
                personData.addAll(result);
                }
        else
        {
            personData.add(new Student("No Result","No Result","No Result","No Result","No Result"));
        }
        search_result.setItems(personData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] typelist= { "Cubicle ID", "Name Student", "Matric number","Check in date", "Supervisor"};
        type.setItems(FXCollections.observableArrayList(typelist));
        matric_column.setCellValueFactory(new PropertyValueFactory<>("matric_no"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        cubicle_column.setCellValueFactory(new PropertyValueFactory<>("cubic_id"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("checkdate"));
        superisor_column.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
    }
}
