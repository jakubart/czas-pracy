package sample.aplikacja;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.alert.AlertDialog;
import sample.database.DodajPracownikaSqlHelper;
import sample.database.PokazCzasSqlHelper;
import sample.database.WstawDaneSqlHelper;
import sample.modul.Pracownik;

public class DodajPracownikaController {

    public CheckBox checkBoxDzial;
    public Button buttonDodaj;
    public TextField textFieldPracownik;
    public TextField textFieldDzial;
    public ComboBox<Pracownik> comboBoxDzialDp;
    public TextField textFieldSzukajPracownika;
    public TableView<Pracownik> tableVievSzukajPrac;
    public CheckBox checkBoxEdytujPrac;
    public TextField textFieldEdytujPrac;
    public TextField textFieldEdytujDzial;
    public Button butonEdytujPrac;
    public TextField textFieldId;
    private boolean isCheckedBoxDzial = false;
    private PokazCzasSqlHelper sqlHelper = new PokazCzasSqlHelper();
    private WstawDaneSqlHelper updateSql = new WstawDaneSqlHelper();
    private DodajPracownikaSqlHelper dodajSql = new DodajPracownikaSqlHelper();

    @FXML
    private void pokazDzial() {
        sqlHelper.pobierzDzial();
        comboBoxDzialDp.setItems(sqlHelper.getListaDzial());
    }

    public void dodajNowyDzial() {
        if (checkBoxDzial.isSelected()) {
            textFieldDzial.setDisable(false);
            comboBoxDzialDp.setDisable(true);
            isCheckedBoxDzial = true;
        } else {
            textFieldDzial.setDisable(true);
            comboBoxDzialDp.setDisable(false);
            isCheckedBoxDzial = false;
        }
    }

    public void dodajPracwnika() {
        if (isCheckedBoxDzial && textFieldPracownik.getLength() > 0 && textFieldDzial.getLength() > 0) {
            dodajSql.dodajPracownika(textFieldPracownik.getText(), textFieldDzial.getText());
            textFieldPracownik.clear();
            textFieldDzial.clear();
        } else if (!isCheckedBoxDzial && textFieldPracownik.getLength() > 0 && !comboBoxDzialDp.getSelectionModel().isEmpty()) {
            dodajSql.dodajPracownika(textFieldPracownik.getText(), String.valueOf(comboBoxDzialDp.getSelectionModel().getSelectedItem()));
            textFieldPracownik.clear();
            comboBoxDzialDp.getSelectionModel().clearSelection();
        } else {
            AlertDialog.zleDaneNowyPracownik();
        }
    }

    public void szukajPracownika() {
        dodajSql.szukajPracownika(textFieldSzukajPracownika.getText());
        tableVievSzukajPrac.setItems(dodajSql.getTabelPracownicy());
    }

    public void edytujPracownika() {
        if (checkBoxEdytujPrac.isSelected()) {
            textFieldEdytujPrac.setDisable(false);
            textFieldEdytujDzial.setDisable(false);
            butonEdytujPrac.setDisable(false);
        } else {
            textFieldEdytujDzial.setDisable(true);
            textFieldEdytujPrac.setDisable(true);
            butonEdytujPrac.setDisable(true);
        }
    }

    //Metoda wstawiająca dane do pól pod tabelą
    public void pobierzZTabeli() {
        if (!tableVievSzukajPrac.getSelectionModel().isEmpty()) {
            textFieldEdytujPrac.setText(tableVievSzukajPrac.getSelectionModel().getSelectedItems().get(0).getPracownik());
            textFieldEdytujDzial.setText(tableVievSzukajPrac.getSelectionModel().getSelectedItems().get(0).getDzial());
            textFieldId.setText(String.valueOf(tableVievSzukajPrac.getSelectionModel().getSelectedItems().get(0).getId()));
        }
    }

    public void edytujPracownikaWBazie() {
        if (!textFieldId.getText().isEmpty() && textFieldEdytujPrac.getLength() > 0 && textFieldEdytujDzial.getLength() > 0) {
            dodajSql.edytujPracownika(textFieldEdytujPrac.getText().toUpperCase(),textFieldEdytujDzial.getText().toUpperCase(), textFieldId.getText());
            dodajSql.szukajPracownika(textFieldEdytujPrac.getText());
        }
    }
}
