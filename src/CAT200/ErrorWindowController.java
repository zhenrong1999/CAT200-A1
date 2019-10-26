package CAT200;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ErrorWindowController {
    @FXML
    private TextArea error_text;
    @FXML
    private Button OkayButton;

    public void setError_text(String error_message){
        error_text.setText(error_message);
    }

    public void close_window() {
        Stage stage = (Stage) OkayButton.getScene().getWindow();
        stage.close();
    }
}
