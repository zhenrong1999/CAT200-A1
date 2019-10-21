package CAT200;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayTableControl implements Initializable {
    @FXML
    private TableColumn matric_column, name_column, cubicle_column, date_column, superisor_column;
    @FXML
    private TableView tableofstudents;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matric_column.setCellValueFactory(new PropertyValueFactory<>("matric_no"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        cubicle_column.setCellValueFactory(new PropertyValueFactory<>("cubic_id"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("checkdate"));
        superisor_column.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
    }
    public void SetToDisplay(Student_Database to_display){
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
        studentObservableList.addAll(to_display);
        tableofstudents.setItems(studentObservableList);
    }
}
