package CAT200;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AdvanceSearchControl {
    public DatePicker start_date, end_date;
    @FXML
    private Button search_button;
    @FXML
    private TextField matric_text, name_text, cubic_id_text, supervisor_text;
    @FXML
    private CheckBox check_matric, check_name, check_cubicle_id, check_check_date, check_supervisor;

    public void search_action() {
        boolean[] check_type = new boolean[5];
        check_type[0] = check_matric.isSelected();
        check_type[1] = check_name.isSelected();
        check_type[2] = check_cubicle_id.isSelected();
        check_type[3] = check_check_date.isSelected();
        check_type[4] = check_supervisor.isSelected();
        String search_item = null;
        System.out.println(start_date.getPromptText());
        if (check_type[0]) {
            search_item += matric_text.getText();
        }
        if (check_type[1]) {
            search_item += name_text.getText();
        }
        if (check_type[2]) {
            search_item += cubic_id_text.getText();
        }
        if (check_type[3]) {
            search_item += start_date.getPromptText();
            search_item += end_date.getValue();
        }
        if (check_type[4]) {
            search_item += supervisor_text.getText();
        }
    }
}