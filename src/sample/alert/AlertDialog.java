package sample.alert;

import javafx.scene.control.Alert;

public class AlertDialog {

    public static void wypelnionyWiersz() {
        Alert alertWypelnionyWiersz = new Alert(Alert.AlertType.ERROR);
        alertWypelnionyWiersz.setTitle("BŁĄD KRYTYCZNY ! ! !");
        alertWypelnionyWiersz.setHeaderText("Pracownik posiada wypełnione godziny tego dnia.");
        alertWypelnionyWiersz.setContentText("Nacisnij Edytuj lub Usuń cały wiersz");
        alertWypelnionyWiersz.showAndWait();
    }

    public static void brakWiersza() {
        Alert alertBrakWiersza = new Alert(Alert.AlertType.WARNING);
        alertBrakWiersza.setTitle("Brak danych");
        alertBrakWiersza.setHeaderText("Podany użytkownik nie ma wpisanych godzin tego dnia.");
        alertBrakWiersza.setContentText("Sprawdź dane i spróbuj ponownie");
        alertBrakWiersza.showAndWait();
    }

    public static void butonEdytuj() {
        Alert alertButtonEdytuj = new Alert(Alert.AlertType.WARNING);
        alertButtonEdytuj.setTitle("Nie można dodać danych");
        alertButtonEdytuj.setHeaderText("Sprawdź czy wszystkie dane zostały wypełnione prawidłowo");
        alertButtonEdytuj.setContentText("Wymagane pola: PRACOWNIK , DZIEŃ, WEJŚCIE, WYJŚCIE, STAWKA");
        alertButtonEdytuj.showAndWait();
    }

    public static void butonUsun() {
        Alert alertButtonUsun = new Alert(Alert.AlertType.WARNING);
        alertButtonUsun.setTitle("Nie można usunąć danych");
        alertButtonUsun.setHeaderText("Sprawdź czy wszystkie dane zostały wypełnione prawidłowo");
        alertButtonUsun.setContentText("Wymagane pola: PRACOWNIK , DZIEŃ");
        alertButtonUsun.showAndWait();
    }

    public static void formatGodziny() {
        Alert alertFormatGodziny = new Alert(Alert.AlertType.ERROR);
        alertFormatGodziny.setTitle("BŁĄD KRYTYCZNY ! ! !");
        alertFormatGodziny.setHeaderText("Został wprowadzony niedopuszczalny format godziny.");
        alertFormatGodziny.setContentText("Poprawny format - GG:mm");
        alertFormatGodziny.showAndWait();
    }

    public static void pracownikWpisany() {
        Alert alertFormatGodziny = new Alert(Alert.AlertType.ERROR);
        alertFormatGodziny.setTitle("BŁĄD KRYTYCZNY ! ! !");
        alertFormatGodziny.setHeaderText("Podany pracownik jest już w bazie danych");
        alertFormatGodziny.setContentText("Sprawdź poprawność danych i spróbuj ponownie");
        alertFormatGodziny.showAndWait();
    }

    public static void zleDaneNowyPracownik() {
        Alert alertNowyPracownik = new Alert(Alert.AlertType.WARNING);
        alertNowyPracownik.setTitle("NIE MOŻNA DODAĆ PRACOWNIKA");
        alertNowyPracownik.setHeaderText("Sprawdź czy wszystkie pola zostały wypełnione prawidłow");
        alertNowyPracownik.setContentText("Wymagane pola: PRACOWNIK, DZIAL");
        alertNowyPracownik.showAndWait();
    }

    public static void dodanyPracownik() {
        Alert alertDodanyPracownik = new Alert(Alert.AlertType.INFORMATION);
        alertDodanyPracownik.setTitle("PRACOWNIK DODANY");
        alertDodanyPracownik.setHeaderText("Pracownik dodany prawidłowo");
        alertDodanyPracownik.setContentText("Naciśnij OK aby kontunować pracę");
        alertDodanyPracownik.showAndWait();
    }

    public static void pokazTabele() {
        Alert alertPokazTabele = new Alert(Alert.AlertType.WARNING);
        alertPokazTabele.setTitle("UZUPEŁNIJ DANE");
        alertPokazTabele.setHeaderText("Sprawdź czy wszystkie dane zostały wybrane");
        alertPokazTabele.setContentText("Pola wymagane: DZIAŁ, PRACOWNIK, ZAKRES OD - OD");
        alertPokazTabele.showAndWait();
    }

    public static void wyierzDzial() {
        Alert alertWybierzDzial = new Alert(Alert.AlertType.INFORMATION);
        alertWybierzDzial.setTitle("Wybierz Dział");
        alertWybierzDzial.setHeaderText("Przed wybraniem pracownika należy wybrać dział");
        alertWybierzDzial.setContentText("Naciśnij OK aby kontunować pracę");
        alertWybierzDzial.showAndWait();
    }

    public static void butonWstawNieobecnosc() {
        Alert alertButtonEdytuj = new Alert(Alert.AlertType.WARNING);
        alertButtonEdytuj.setTitle("Nie można dodać danych");
        alertButtonEdytuj.setHeaderText("Sprawdź czy wszystkie dane zostały wypełnione prawidłowo");
        alertButtonEdytuj.setContentText("Wymagane pola: PRACOWNIK , DZIEŃ, RODZAJ NIEOBECNOŚCI");
        alertButtonEdytuj.showAndWait();
    }

    public static void edytowanyPracownik() {
        Alert alertDodanyPracownik = new Alert(Alert.AlertType.INFORMATION);
        alertDodanyPracownik.setTitle("PRACOWNIK EDYTOWANY");
        alertDodanyPracownik.setHeaderText("Pracownik zmieniony prawidłowo");
        alertDodanyPracownik.setContentText("Naciśnij OK aby kontunować pracę");
        alertDodanyPracownik.showAndWait();
    }

    public static void brakDanychdoExcel() {
        Alert alertBrakdanychDoExcel= new Alert(Alert.AlertType.WARNING);
        alertBrakdanychDoExcel.setTitle("Nie można pobrać danych");
        alertBrakdanychDoExcel.setHeaderText("Brak danych do zapisania w Excelu");
        alertBrakdanychDoExcel.setContentText("Sprawdź dane i ponów próbę");
        alertBrakdanychDoExcel.showAndWait();
    }

    public static void zapisanyDoExcel() {
        Alert alertzapisanyExcel = new Alert(Alert.AlertType.INFORMATION);
        alertzapisanyExcel.setTitle("PLIK ZAPISANY DO EXCEL");
        alertzapisanyExcel.setHeaderText("Plik został zapisany w folderze \\Dokument\\Java ECP");
        alertzapisanyExcel.setContentText("Naciśnij OK aby kontunować pracę");
        alertzapisanyExcel.showAndWait();
    }

    public static void bladDostepuExcell() {
        Alert alertBladDostepu= new Alert(Alert.AlertType.WARNING);
        alertBladDostepu.setTitle("Nie można zapisać pliku");
        alertBladDostepu.setHeaderText("Zamknij program Excel i spróbuj ponownie");
        alertBladDostepu.showAndWait();
    }

    public static void nieMoznaUtworzycKatalogu() {
        Alert alertBladDostepu= new Alert(Alert.AlertType.WARNING);
        alertBladDostepu.setTitle("Nie można zapisać pliku");
        alertBladDostepu.setHeaderText("Zamknij program Excel i spróbuj ponownie");
        alertBladDostepu.showAndWait();
    }

    public static void nieMaUsera() {
        Alert alertBladDostepu= new Alert(Alert.AlertType.ERROR);
        alertBladDostepu.setTitle("Błąd krytyczny ! ");
        alertBladDostepu.setHeaderText("Podaj poprawne dane i spróbuj ponownie");
        alertBladDostepu.showAndWait();
    }
}