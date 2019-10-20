package CAT200;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
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
            {Title.setText("Exit");
            // get a handle to the stage
            Stage stage = (Stage) Exit.getScene().getWindow();
            stage.close();}
    }

    @FXML
    private Button submit;
    @FXML
    private TextField cubic_id ;
    @FXML
    private TextField name ;
    @FXML
    private TextField matricNum ;
    @FXML
    private TextField checkdate ;
    @FXML
    private TextField supervisor ;

    public Student userClickSubmit(){
        Student newStud = new Student(cubic_id.getText(), name.getText(), matricNum.getText(), checkdate.getText(), supervisor.getText());

        return newStud;
    }



    @FXML
    private TextField search_text;
    @FXML
    private Button search_button;
    @FXML
    private ChoiceBox<String> type;

    public void search_in_action() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] typelist= { "Cubicle ID", "Name Student", "Matric number","Check in date", "Supervisor"};
        type.setItems(FXCollections.observableArrayList(typelist));
    }
}
