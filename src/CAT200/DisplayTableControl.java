package CAT200;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private TableColumn<Object, String> matric_column;
    @FXML
    private TableColumn<Object, String> name_column;
    @FXML
    private TableColumn<Object, String> cubicle_column;
    @FXML
    private TableColumn<Object, String> date_column;
    @FXML
    private TableColumn<Object, String> supervisor_column;
    @FXML
    private TableColumn<CheckBox, Boolean> to_delete_column;
    @FXML
    private Button discard_button, delete_button;
    private MainWindowController mainWindowController;
    private Student_Database student_database_clone;
    private boolean edited = false;
    private String search_type, search_item;
    private Student_Database result;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matric_column.setCellValueFactory(new PropertyValueFactory<Object, String>("matric_no"));
        name_column.setCellValueFactory(new PropertyValueFactory<Object, String>("name"));
        cubicle_column.setCellValueFactory(new PropertyValueFactory<Object, String>("cubic_id"));
        date_column.setCellValueFactory(new PropertyValueFactory<Object, String>("checkdate"));
        supervisor_column.setCellValueFactory(new PropertyValueFactory<Object, String>("supervisor"));
        to_delete_column.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        table_of_students.setEditable(true);
        matric_column.setCellFactory(TextFieldTableCell.forTableColumn());
        name_column.setCellFactory(TextFieldTableCell.forTableColumn());
        cubicle_column.setCellFactory(TextFieldTableCell.forTableColumn());
        date_column.setCellFactory(TextFieldTableCell.forTableColumn());
        supervisor_column.setCellFactory(TextFieldTableCell.forTableColumn());
        //to_delete_column.setSortType();

        student_database_clone = MainWindowController.student_database.deep_clone();
    }

    public void onEditChanged(TableColumn.CellEditEvent<Object, String> studentString) {
        edited = true;
        Student student = table_of_students.getSelectionModel().getSelectedItem();
        if (studentString.getSource() == matric_column) {
            student.setMatric_no(studentString.getNewValue());
        } else if (studentString.getSource() == name_column) {
            student.setName(studentString.getNewValue());
        } else if (studentString.getSource() == cubicle_column)
            student.setCubic_id(studentString.getNewValue());
        else if (studentString.getSource() == date_column)
            student.setCheckdate(studentString.getNewValue());
        else if (studentString.getSource() == supervisor_column)
            student.setSupervisor(studentString.getNewValue());
        String error_message = student.validation();
        if (!error_message.equals("")) {
            mainWindowController.error_message_box(error_message);
            if (studentString.getSource() == matric_column) {
                student.setMatric_no(studentString.getOldValue());
            } else if (studentString.getSource() == name_column) {
                student.setName(studentString.getOldValue());
            } else if (studentString.getSource() == cubicle_column)
                student.setCubic_id(studentString.getOldValue());
            else if (studentString.getSource() == date_column)
                student.setCheckdate(studentString.getOldValue());
            else if (studentString.getSource() == supervisor_column)
                student.setSupervisor(studentString.getOldValue());
        }
    }

    public void discard_changes() {
        if (edited) {
            MainWindowController.student_database = student_database_clone.deep_clone();
            SetToDisplay(search_type, search_item);
        }
    }

    public void to_delete() {
        ObservableList<Student> data_to_remove = FXCollections.observableArrayList();

        for (Student stu_delete : result) {
            if (stu_delete.getCheckbox().isSelected())
                data_to_remove.add(stu_delete);
        }
        MainWindowController.student_database.removeAll(data_to_remove);
        SetToDisplay(search_type, search_item);
    }

    void SetToDisplay(String search_type, String search_item) {
        this.search_type = search_type;
        this.search_item = search_item;
        if (search_type.equals("ALL"))
            result = MainWindowController.student_database.searchForAllwithMultiple(search_item);
        else if (search_type.equals("Display All"))
            result = MainWindowController.student_database;
        else
            result = MainWindowController.student_database.searchForAll(search_type, search_item);

        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
        if (result.size() != 0) {
            studentObservableList.addAll(result);
            discard_button.setVisible(true);
            delete_button.setVisible(true);
        } else {
            studentObservableList.add(new Student("No Result", "No Result", "No Result", "No Result", "No Result"));
            discard_button.setVisible(false);
            delete_button.setVisible(false);
        }
        table_of_students.setItems(studentObservableList);
    }

}
