package CAT200;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    File_Handling reader = new File_Handling("database.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
        Parent root = loader.load();
        LoginWindowControl loginWindowControl = loader.getController();
        //MainWindowController mainWindowController = loader.getController();
        //mainWindowController.student_database.addFromLinkedList(reader.getData()); //Extract raw data from the file reader into structured data
        loginWindowControl.setMainApp(this);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 430));
        primaryStage.show();
    }
}
