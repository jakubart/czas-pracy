package sample.aplikacja;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.alert.AlertDialog;
import sample.database.PokazCzasSqlHelper;
import sample.database.WstawDaneSqlHelper;
import sample.modul.CzasPracy;
import sample.modul.Pracownik;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PokazCzasControl {

    public ComboBox<Pracownik> comboBoxDzial;
    public ComboBox<Pracownik> comboBoxPracownik;
    public Button buttonPokazTabele;
    public DatePicker dataPoczatek;
    public DatePicker dataKoniec;
    public TableView<CzasPracy> tableVievTabela;
    public Label labelSumaPole;
    public HBox hBoxEdytuj;
    public DatePicker dataPickerEdytujGodiny;
    public TextField textFieldCzasWejscie;
    public TextField textFieldCzasWyjscie;
    public CheckBox checkBoxEdytuj;
    public Button buttonWstaw;
    public CheckBox checkBoxNieobecnosc;
    public ComboBox<String> comboBoxNieobecnosc;
    public HBox hboxNieobecnosc;
    public ComboBox<String> comboBoxStawka;
    private PokazCzasSqlHelper sqlHelper = new PokazCzasSqlHelper();
    private WstawDaneSqlHelper updateSql = new WstawDaneSqlHelper();
    private ObservableList<String> listaNieobecnosci = FXCollections.observableArrayList("URLOP", "ZWOLNIENIE", "NIEUSPRAWIEDLIWIONE");
    private ObservableList<String> listaStawek = FXCollections.observableArrayList("12DC", "12DC/17A", "12DH", "17A");


    public void initialize() {
        pokazDzial();
    }

    private void pokazDzial() {
        sqlHelper.pobierzDzial();
        comboBoxDzial.setItems(sqlHelper.getListaDzial());
        comboBoxStawka.setItems(listaStawek);
    }

    public void pokazPracownika() {
        try {
            sqlHelper.pobierzPracownika(comboBoxDzial.getSelectionModel().getSelectedItem().toString());
            comboBoxPracownik.setItems(sqlHelper.getListaPracownik());
        } catch (NullPointerException n) {
            AlertDialog.wyierzDzial();
        }
    }

    public void pokazTabele() {
        if (!comboBoxDzial.getSelectionModel().isEmpty() && !comboBoxPracownik.getSelectionModel().isEmpty()) {
            sqlHelper.pobierzTabele(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPoczatek.getValue(), dataKoniec.getValue());
            tableVievTabela.setItems(sqlHelper.getTabelCzas());
            pokazSume();
        } else {
            AlertDialog.pokazTabele();
        }
    }

    private void pokazSume() {
        labelSumaPole.setVisible(true);
        labelSumaPole.setText("SUMA PRZEPRACOWANYCH GODZIN: " + sqlHelper.getGodzin() + ":" + sqlHelper.getMinut());
    }

    public void checkEdytujDane() {
        if (checkBoxEdytuj.isSelected()) {
            hBoxEdytuj.setVisible(true);
            hboxNieobecnosc.setVisible(true);
        } else {
            hBoxEdytuj.setVisible(false);
            hboxNieobecnosc.setVisible(false);
        }
    }

    // Metoda dodaje wiersz do tabeli

    public void dodajDzienObj() {
        try {
            if (!checkBoxNieobecnosc.isSelected()) {
                if (sprawdzDane() && warunek()) {
                    updateSql.wstawDzienObiekt(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPickerEdytujGodiny.getValue(),
                            textFieldCzasWejscie.getText(), textFieldCzasWyjscie.getText(), comboBoxStawka.getSelectionModel().getSelectedItem().toString());
                    pokazTabele();
                    dataPickerEdytujGodiny.setValue(dataPickerEdytujGodiny.getValue().plusDays(1));
                } else {
                    AlertDialog.butonEdytuj();
                }
            } else if (sprawdzDane() && !comboBoxNieobecnosc.getSelectionModel().isEmpty()) {
                updateSql.wstawNieobecnosc(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPickerEdytujGodiny.getValue(),
                        comboBoxNieobecnosc.getSelectionModel().getSelectedItem());
                pokazTabele();
                dataPickerEdytujGodiny.setValue(dataPickerEdytujGodiny.getValue().plusDays(1));
            } else
                AlertDialog.butonWstawNieobecnosc();
        } catch (IndexOutOfBoundsException e) {
            AlertDialog.formatGodziny();
        }
    }

    //Metoda edytuje wiersz w tabeli

    public void edytujGodzinyObj() {
        try {
            if (!checkBoxNieobecnosc.isSelected()) {
                if (sprawdzDane() && warunek()) {
                    updateSql.edytujDaneObiekt(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPickerEdytujGodiny.getValue(),
                            textFieldCzasWejscie.getText(), textFieldCzasWyjscie.getText(), comboBoxStawka.getSelectionModel().getSelectedItem());
                    pokazTabele();
                } else {
                    AlertDialog.butonEdytuj();
                }
            } else if (sprawdzDane() && !comboBoxNieobecnosc.getSelectionModel().isEmpty()) {
                updateSql.edytujNieobecnosc(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPickerEdytujGodiny.getValue(),
                        comboBoxNieobecnosc.getSelectionModel().getSelectedItem());
                pokazTabele();
            }
        } catch (IndexOutOfBoundsException e) {
            AlertDialog.formatGodziny();
        }
    }

    public void usunDzien() {
        if (sprawdzDane()) {
            updateSql.skasujDane(comboBoxPracownik.getSelectionModel().getSelectedItem().toString(), dataPickerEdytujGodiny.getValue());
            pokazTabele();
        } else {
            AlertDialog.butonUsun();
        }
    }

    //Metoda sprawdza format godziny

    private boolean sprawdzGodziny(String czasGodz, String czasMinu) {
        boolean czasZgodny = false;
        try {
            if (Integer.parseInt(czasGodz) < 24 && Integer.parseInt(czasMinu) < 60) {
                czasZgodny = true;
            } else {
                czasZgodny = false;
            }
        } catch (NumberFormatException nb) {
            AlertDialog.formatGodziny();
        }
        return czasZgodny;
    }

    private boolean sprawdzDane() {
        boolean sprawdzUsun;
        if (comboBoxDzial.getSelectionModel().getSelectedItem() != null && dataPickerEdytujGodiny.getValue() != null) {
            sprawdzUsun = true;
        } else {
            sprawdzUsun = false;
        }
        return sprawdzUsun;
    }

    public void checkNieobecnosc() {
        if (checkBoxNieobecnosc.isSelected()) {
            comboBoxNieobecnosc.setItems(listaNieobecnosci);
            textFieldCzasWejscie.setDisable(true);
            textFieldCzasWyjscie.setDisable(true);
            comboBoxStawka.setDisable(true);
            comboBoxNieobecnosc.setDisable(false);
        } else {
            textFieldCzasWejscie.setDisable(false);
            textFieldCzasWyjscie.setDisable(false);
            comboBoxStawka.setDisable(false);
            comboBoxNieobecnosc.setDisable(true);
        }
    }

    // Metoda wstawia dane z wirsza do pól pod tabelką

    public void pobierzZTabeli() {
        if (!tableVievTabela.getSelectionModel().isEmpty()) {
            textFieldCzasWyjscie.setText(tableVievTabela.getSelectionModel().getSelectedItems().get(0).getWyjscie().toString());
            textFieldCzasWejscie.setText(tableVievTabela.getSelectionModel().getSelectedItems().get(0).getWejscie().toString());
            dataPickerEdytujGodiny.setValue(tableVievTabela.getSelectionModel().getSelectedItems().get(0).getData_wejscie());
            comboBoxStawka.setValue(tableVievTabela.getSelectionModel().getSelectedItem().getStawka());
        }
    }

    // Metoda sprawdzająca czy wszystkie pola potrzebne do wstawienia do tabeli są wypełnione

    private boolean warunek() {
        boolean warunek;
        if (sprawdzGodziny(textFieldCzasWejscie.getText().substring(0, 2), textFieldCzasWejscie.getText().substring(3, 5))
                && sprawdzGodziny(textFieldCzasWyjscie.getText().substring(0, 2), textFieldCzasWyjscie.getText().substring(3, 5)) && !comboBoxStawka.getSelectionModel().isEmpty())
            warunek = true;
        else
            warunek = false;
        return warunek;
    }

    public String utworzKatologDoZapisu(String nazwaKatalogu){
        String katalogGlowny = FileSystemView.getFileSystemView().getDefaultDirectory() + "\\Java ECP";
        File dox = new File(katalogGlowny);
        String katalogPodrzedny = FileSystemView.getFileSystemView().getDefaultDirectory() + "\\Java ECP\\"+nazwaKatalogu;
        File doxDrugi = new File(katalogPodrzedny);
        if (!dox.isDirectory()) {
            try {
                Files.createDirectory(Paths.get(katalogGlowny));
                Files.createDirectory(Paths.get(katalogPodrzedny));
            } catch (IOException e) {
                System.out.println("Nie mozna utworzyc katalogu" + e.getMessage());
                AlertDialog.nieMoznaUtworzycKatalogu();
            }
        } else if (!doxDrugi.isDirectory()) {
            try {
                Files.createDirectory(Paths.get(katalogPodrzedny));
            } catch (IOException e) {
                System.out.println("Nie mozna utworzyc katalogu" + e.getMessage());
                AlertDialog.nieMoznaUtworzycKatalogu();
            }
        }
        return katalogPodrzedny;
    }

    public void pobierzExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet shet = workbook.createSheet("Czas Pracy");
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            XSSFCellStyle hederStyle = workbook.createCellStyle();
            Font dataFontHeder = workbook.createFont();
            hederStyle.setVerticalAlignment(VerticalAlignment.TOP);
            hederStyle.setAlignment(HorizontalAlignment.CENTER);
            dataFontHeder.setBold(true);
            dataFontHeder.setFontHeightInPoints((short) 16);
            hederStyle.setFont(dataFontHeder);
            cellStyle.setBorderTop(BorderStyle.THIN);
            XSSFRow header = shet.createRow(0);
            shet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            header.createCell(0).setCellValue("Ewidencja Czasu pracy");
            header.getCell(0).setCellStyle(hederStyle);
            XSSFRow header2 = shet.createRow(3);
            //header2.createCell(0).setCellValue("Pracownik");
            header2.createCell(0).setCellValue("Data Wejśćie");
            header2.createCell(1).setCellValue("Czas Wejśćie");
            header2.createCell(2).setCellValue("Czas Wyjście");
            header2.createCell(3).setCellValue("Suma");
            header2.createCell(4).setCellValue("Stawka");
            header2.createCell(5).setCellValue("Nieobecnosc");
            header.setHeightInPoints(40);
            shet.setDefaultColumnWidth(11);
            shet.setColumnWidth(0, 7500);
            shet.setColumnWidth(4, 2000);
            XSSFRow header3 = shet.createRow(1);
            header3.createCell(0).setCellValue("Pracwnik: "+comboBoxPracownik.getSelectionModel().getSelectedItem().toString());
            header3.createCell(2).setCellValue("Dział:");
            header3.createCell(3).setCellValue(comboBoxDzial.getSelectionModel().getSelectedItem().toString());
            int i = 4;
            XSSFRow row;
            for (CzasPracy listaExcelCzas : tableVievTabela.getItems()) {
                row = shet.createRow(i);
                //row.createCell(0).setCellValue(listaExcelCzas.getPracownik());
                row.createCell(0).setCellValue(listaExcelCzas.getData_wejscie().toString());
                row.createCell(1).setCellValue(listaExcelCzas.getWejscie().toString());
                row.createCell(2).setCellValue(listaExcelCzas.getWyjscie().toString());
                row.createCell(3).setCellValue(listaExcelCzas.getSuma().toString());
                row.createCell(4).setCellValue(listaExcelCzas.getStawka());
                row.createCell(5).setCellValue(listaExcelCzas.getNieobecnosc());
                //row.getCell(0).setCellStyle(cellStyle);
                row.getCell(0).setCellStyle(cellStyle);
                row.getCell(1).setCellStyle(cellStyle);
                row.getCell(2).setCellStyle(cellStyle);
                row.getCell(3).setCellStyle(cellStyle);
                row.getCell(4).setCellStyle(cellStyle);
                row.getCell(5).setCellStyle(cellStyle);
                i++;
            }
            FileOutputStream out = new FileOutputStream(utworzKatologDoZapisu("Czas Pracy") +
                    "\\"+comboBoxPracownik.getSelectionModel().getSelectedItem().toString()+" "+
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)+"-"+
                    LocalDateTime.now().format(DateTimeFormatter.ISO_TIME).substring(0,8).replace(':','-')+".xlsx");
            //FileOutputStream out = new FileOutputStream(home + "\\EXCEL Czas Pracy\\Czas Pracy.xlsx");
            workbook.write(out);
            out.close();
            AlertDialog.zapisanyDoExcel();
            System.out.println("Excel zapisany");
        } catch (FileNotFoundException e) {
            AlertDialog.bladDostepuExcell();
            System.out.println("Błąd dostępu do excel");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            AlertDialog.brakDanychdoExcel();
        }
    }
}

