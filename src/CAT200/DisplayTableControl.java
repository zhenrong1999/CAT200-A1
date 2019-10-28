package CAT200;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayTableControl implements Initializable {
    @FXML
    public AnchorPane pane_table;
    @FXML
    public TableView<Student> table_of_students;
    @FXML
    public Label label_list_of_student;
    @FXML
    private TableColumn<?, ?> matric_column;
    @FXML
    private TableColumn<?, ?> name_column;
    @FXML
    private TableColumn<?, ?> cubicle_column;
    @FXML
    private TableColumn<?, ?> date_column;
    @FXML
    private TableColumn<?, ?> supervisor_column;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matric_column.setCellValueFactory(new PropertyValueFactory<>("matric_no"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        cubicle_column.setCellValueFactory(new PropertyValueFactory<>("cubic_id"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("checkdate"));
        supervisor_column.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
    }

    void SetToDisplay(Student_Database to_display) {
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
        if (to_display.size() != 0) {
            studentObservableList.addAll(to_display);
        } else {
            studentObservableList.add(new Student("No Result", "No Result", "No Result", "No Result", "No Result"));
        }
        table_of_students.setItems(studentObservableList);
    }
}
