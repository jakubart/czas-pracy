package sample.aplikacja;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TopMenuButton topMenuController;

    @FXML
    private void initialize() {
        topMenuController.setMainController(this);
    }

    public void setCenter(String fxmlPlik) {
        FXMLLoader loaderFxml = null;
        Parent parent = null;
        try {
            loaderFxml = new FXMLLoader(this.getClass().getResource(fxmlPlik));
            parent = loaderFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROE LOADING BORDER PANE " + e.getMessage());
        }
        borderPane.setCenter(parent);
    }
}
