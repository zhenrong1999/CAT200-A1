package CAT200;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class LoginWindowControl implements Initializable {
    //Kee Xian
    public LinkedList<AdminInfo> adminList = new LinkedList<AdminInfo>();
    public File_Handling reader = new File_Handling("database.txt");
    public String user, pwd;
    File_Handling file_handling = new File_Handling("adminFile.txt");
    private Main main;
    @FXML
    private Label error;
    @FXML
    private javafx.scene.control.Button LoginBtn;
    @FXML
    private javafx.scene.control.TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane mainWindow;
    //Kee Xian

    public void setMainApp(Main a) {
        this.main = a;
    }

    public void printLinkedList() {
        for (int i = 0; i < adminList.size(); i++)
            adminList.get(i).print();
    }

    public void submitClicked(ActionEvent event) {
        Boolean found = false;
        for (int i = 0; i < adminList.size(); i++) {
            if (username.getText().equals(adminList.get(i).getName()) && password.getText().equals(adminList.get(i).getPassword())) {
                found = true;
                user = username.getText();
                pwd = password.getText();
            }
        }
        if (!found) {
            error.setText("Username or password error. Please try again");
            error.setVisible(true);
        } else {
            error.setText("Login Successful");
            error.setVisible(true);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                mainWindow.getChildren().setAll((AnchorPane) loader.load());
                MainWindowController mainWindowController = loader.getController();
                mainWindowController.student_database.addFromLinkedList(reader.getData()); //Extract raw data
                mainWindowController.setMainApp(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        file_handling.ReadFromFile();
        String[] data_extracted;
        reader.ReadFromFile();
        for (int i = 0; i < file_handling.getData().size(); i++) {
            data_extracted = file_handling.getData().get(i).split(" ");
            try {
                AdminInfo newAdmin = new AdminInfo(data_extracted[0], data_extracted[1]);
                adminList.add(newAdmin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        error.setVisible(false);
    }

}
