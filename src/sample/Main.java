package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.ConnectionHelper;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/plikiFXML/fxmlMainControler.fxml"));
        primaryStage.setTitle("Czas Pracy v. 1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        ConnectionHelper.getConnection();

    }
}
