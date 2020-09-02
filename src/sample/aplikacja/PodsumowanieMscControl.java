package sample.aplikacja;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.alert.AlertDialog;
import sample.database.PokazCzasSqlHelper;
import sample.modul.CzasPracy;
import sample.modul.Pracownik;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PodsumowanieMscControl {

    public ComboBox<Pracownik> comboBoxDzial;
    public DatePicker dataPickerOd;
    public DatePicker dataPickerDo;
    public Button buttonPokaz;
    public TableView<CzasPracy> tableViewPodsumowanie;
    private PokazCzasSqlHelper sqlHelper = new PokazCzasSqlHelper();
    private PokazCzasControl czasControl = new PokazCzasControl();

    public void initialize() {
        pokazDzial();
    }

    private void pokazDzial() {
        sqlHelper.pobierzDzial();
        comboBoxDzial.setItems(sqlHelper.getListaDzial());
    }

    public void pokazPodsumowanie() {
        if (!comboBoxDzial.getSelectionModel().isEmpty())
            sqlHelper.podsumowanieMiesiaca(comboBoxDzial.getSelectionModel().getSelectedItem().toString(), dataPickerOd.getValue(), dataPickerDo.getValue());
        tableViewPodsumowanie.setItems(sqlHelper.getTabelaPodsumowanie());
    }



    public void pobierzExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet shet = workbook.createSheet("Podsumowanie");
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            shet.setColumnWidth(0, 7500);
            shet.setColumnWidth(1, 4000);
            XSSFRow header = shet.createRow(0);
            header.createCell(0).setCellValue("Dział: " + comboBoxDzial.getSelectionModel().getSelectedItem());
            header.createCell(2).setCellValue("Okres: " + dataPickerDo.getValue().getMonth() + ", " + dataPickerDo.getValue().getYear());
            XSSFRow header2 = shet.createRow(2);
            header2.createCell(0).setCellValue("Pracownicy");
            header2.createCell(1).setCellValue("Suma godzin");
            int i = 3;
            XSSFRow row;
            for (CzasPracy listaExcel : tableViewPodsumowanie.getItems()) {
                row = shet.createRow(i);
                row.createCell(0).setCellValue(listaExcel.getPracownik());
                row.createCell(1).setCellValue(listaExcel.getPodsumowanie());
                row.getCell(0).setCellStyle(cellStyle);
                row.getCell(1).setCellStyle(cellStyle);
                i++;
            }
            //File home = FileSystemView.getFileSystemView().getHomeDirectory();
            FileOutputStream out = new FileOutputStream( czasControl.utworzKatologDoZapisu("Podsumowanie")+
                    "\\"+ comboBoxDzial.getSelectionModel().getSelectedItem().toString() + "_" + dataPickerOd.getValue().getMonthValue() +
                    "-"+ LocalDateTime.now().format(DateTimeFormatter.ISO_TIME).substring(0,8).replace(':','-')+".xlsx");
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
