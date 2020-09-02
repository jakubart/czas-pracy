package sample.database;

import sample.alert.AlertDialog;
import sample.modul.CzasPracy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WstawDaneSqlHelper {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    // Metoda tworzy obiekt na wzór wyświetlanej tabeli

    private CzasPracy getCzasPracy(String pracownik, LocalDate dataWejscie, String godz_wejscie, String godz_wyjscie, String stawka) {
        LocalTime localTimeGodzinaWejscie = LocalTime.parse(godz_wejscie, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime localTimeGodzinaWyjscie = LocalTime.parse(godz_wyjscie, DateTimeFormatter.ISO_LOCAL_TIME);
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownik(pracownik);
        czasPracy.setData_wejscie(dataWejscie);
        czasPracy.setWejscie(localTimeGodzinaWejscie);
        czasPracy.setWyjscie(localTimeGodzinaWyjscie);
        czasPracy.setStawka(stawka);
        return czasPracy;
    }

    //Metoda edytuje wierz w tabeli

    public void edytujDaneObiekt(String pracownik, LocalDate dataWejscie, String godz_wejscie, String godz_wyjscie, String stawka) {
        try {
            CzasPracy czasPracy = getCzasPracy(pracownik, dataWejscie, godz_wejscie, godz_wyjscie, stawka);
            if (sprawdzCzyWypelniony(czasPracy.getPracownik(), czasPracy.getData_wejscie())) {
                conn = ConnectionHelper.getConnection();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("update czas_pracy.godziny c left join pracownicy p on id_pracownik =  p.id_prac " +
                            "set wejscie = '" + czasPracy.getWejscie() + "', wyjscie = '" + czasPracy.getWyjscie() + "', czas_pracy ='" + czasPracy.sumaGodzin() +
                            "', stawka = '" + czasPracy.getStawka() + "', nieobecnosc = NULL where pracownik = '" + czasPracy.getPracownik() + "' AND data_wejscie = '" + czasPracy.getData_wejscie() + "'");
                    System.out.println("Dane wstawione OBJ");
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.brakWiersza();
            }
        } catch (DateTimeParseException e) {
            AlertDialog.formatGodziny();
        }
    }

    public void wstawDzienObiekt(String pracownik, LocalDate dataWejscie, String godz_wejscie, String godz_wyjscie, String stawka) {
        try {
            CzasPracy czasPracy = getCzasPracy(pracownik, dataWejscie, godz_wejscie, godz_wyjscie, stawka);
            if (sprawdzCzyWypelniony(czasPracy.getPracownik(), czasPracy.getData_wejscie())) {
                AlertDialog.wypelnionyWiersz();
            } else {
                conn = ConnectionHelper.getConnection();
                try {
                    stmt = conn.createStatement();
                    stmt.executeUpdate("insert into czas_pracy.godziny SET id_pracownik = (select id_prac from pracownicy where pracownik = '" + czasPracy.getPracownik() + "'), " +
                            "data_wejscie = '" + czasPracy.getData_wejscie() + "', wejscie = '" + czasPracy.getWejscie() + "', wyjscie = '" + czasPracy.getWyjscie() + "',czas_pracy = '"
                            + czasPracy.sumaGodzin() + "', stawka = '" + czasPracy.getStawka() + "'");
                    System.out.println("Dane dodane OBJ");
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (DateTimeParseException e) {
            AlertDialog.formatGodziny();
        }
    }

    public void wstawNieobecnosc(String pracownik, LocalDate dataWejscie, String nieobecnosc) {
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownik(pracownik);
        czasPracy.setData_wejscie(dataWejscie);
        czasPracy.setNieobecnosc(ustalTypNieobecnosc(nieobecnosc));
        if (sprawdzCzyWypelniony(czasPracy.getPracownik(), czasPracy.getData_wejscie())) {
            AlertDialog.wypelnionyWiersz();
        } else {
            conn = ConnectionHelper.getConnection();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("insert into czas_pracy.godziny SET id_pracownik = (select id_prac from pracownicy where pracownik = '" + czasPracy.getPracownik() + "'), " +
                        "data_wejscie = '" + czasPracy.getData_wejscie() + "', wejscie = '" + 0 + "', wyjscie = '" + 0 + "',czas_pracy = '" + 0 + "'" +
                        ",stawka = '" + "-" + "', nieobecnosc = '" + czasPracy.getNieobecnosc() + "'");
                System.out.println("Nieobecnosc wstawiona OBJ");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void edytujNieobecnosc(String pracownik, LocalDate dataWejscie, String nieobecnosc) {
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownik(pracownik);
        czasPracy.setData_wejscie(dataWejscie);
        czasPracy.setNieobecnosc(ustalTypNieobecnosc(nieobecnosc));
        if (sprawdzCzyWypelniony(czasPracy.getPracownik(), czasPracy.getData_wejscie())) {
            conn = ConnectionHelper.getConnection();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("update czas_pracy.godziny c left join pracownicy p on id_pracownik =  p.id_prac " +
                        "set wejscie = '" + 0 + "', wyjscie = '" + 0 + "', czas_pracy ='" + 0 + "', stawka = '" + "-" +
                        "',nieobecnosc = '" + czasPracy.getNieobecnosc() + "'  where pracownik = '" + czasPracy.getPracownik() + "' AND data_wejscie = '" + czasPracy.getData_wejscie() + "'");
                System.out.println("Nieobecnosc edytowana OBJ");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            AlertDialog.brakWiersza();
        }
    }

    private boolean sprawdzCzyWypelniony(String pracownik, LocalDate dataWejscie) {
        boolean wierszWypelniony = false;
        conn = ConnectionHelper.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT pracownik, dzial, data_wejscie, wejscie, wyjscie FROM czas_pracy.godziny c " +
                    "left join pracownicy p on id_pracownik =  p.id_prac where pracownik ='" + pracownik + "' AND data_wejscie = '" + dataWejscie + "'");
            if (rs.next()) {
                wierszWypelniony = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wierszWypelniony;
    }

    public void skasujDane(String pracownik, LocalDate dataWejscie) {
        if (sprawdzCzyWypelniony(pracownik, dataWejscie)) {
            conn = ConnectionHelper.getConnection();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE czas_pracy.godziny from czas_pracy.godziny LEFT JOIN pracownicy p ON id_pracownik = p.id_prac " +
                        "WHERE pracownik = '" + pracownik + "' AND data_wejscie = '" + dataWejscie + "'");
                System.out.println("Dane skasowane");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            AlertDialog.brakWiersza();
        }
    }

    private String ustalTypNieobecnosc(String nazwaNieobecnosc) {
        switch (nazwaNieobecnosc) {
            case "URLOP":
                nazwaNieobecnosc = "URLOP";
                break;
            case "ZWOLNIENIE":
                nazwaNieobecnosc = "ZW";
                break;
            case "NIEUSPRAWIEDLIWIONE":
                nazwaNieobecnosc = "NB";
                break;
        }
        return nazwaNieobecnosc;
    }
}
