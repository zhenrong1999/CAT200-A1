package CAT200;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    @FXML
    private Button Home,Add,Search,Exit;
    @FXML
    private Label Title;
    @FXML
    private void handlebuttonaction(ActionEvent event){
        if(event.getSource()==Home)
            Title.setText("Home");
        else if(event.getSource()==Add)
            Title.setText("Add Student");
        else if (event.getSource()==Search)
            Title.setText("Search Student");
        else if(event.getSource()==Exit)
            Title.setText("Exit");
            // get a handle to the stage
            Stage stage = (Stage) Exit.getScene().getWindow();
            stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
