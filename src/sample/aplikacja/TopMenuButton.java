package sample.aplikacja;

import javafx.fxml.FXML;

public class TopMenuButton {

    private static final String FXMLPOKAZCZAS = "/sample/plikiFXML/fxmlPokazCzas.fxml";
    private static final String FXMLDODAJPRACOWNIKA = "/sample/plikiFXML/fxmlDodajPracownika.fxml";
    private static final String FXMLPODSUMOWANIE = "/sample/plikiFXML/fxmlPodsumowanieMiesiaca.fxml";
    private MainController mainController;

    @FXML
    public void actionDodajPracownika() {
        mainController.setCenter(FXMLDODAJPRACOWNIKA);
    }

    @FXML
    public void actionPokazCzas() {
        mainController.setCenter(FXMLPOKAZCZAS);
    }

    @FXML
    public void actionPodsumowanieMiesiaca() {
        mainController.setCenter(FXMLPODSUMOWANIE);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
