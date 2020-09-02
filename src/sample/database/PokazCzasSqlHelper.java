package sample.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.alert.AlertDialog;
import sample.modul.CzasPracy;
import sample.modul.Pracownik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PokazCzasSqlHelper {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private ObservableList<Pracownik> listaDzial = FXCollections.observableArrayList();
    private ObservableList<Pracownik> listaPracownik = FXCollections.observableArrayList();
    private ObservableList<CzasPracy> tabelCzas = FXCollections.observableArrayList();
    private ObservableList<CzasPracy> tabelaPodsumowanie = FXCollections.observableArrayList();
    private int godzin;
    private int minut;

    public int getGodzin() {
        return godzin;
    }

    public void setGodzin(int godzin) {
        this.godzin = godzin;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }

    public ObservableList<CzasPracy> getTabelCzas() {
        return tabelCzas;
    }

    public void setTabelCzas(ObservableList<CzasPracy> tabelCzas) {
        this.tabelCzas = tabelCzas;
    }

    public ObservableList<Pracownik> getListaPracownik() {
        return listaPracownik;
    }

    public void setListaPracownik(ObservableList<Pracownik> listaPracownik) {
        this.listaPracownik = listaPracownik;
    }

    public ObservableList<Pracownik> getListaDzial() {
        return listaDzial;
    }

    public void setListaDzial(ObservableList<Pracownik> listaDzial) {
        this.listaDzial = listaDzial;
    }

    public ObservableList<CzasPracy> getTabelaPodsumowanie() {
        return tabelaPodsumowanie;
    }

    public void setTabelaPodsumowanie(ObservableList<CzasPracy> tabelaPodsumowanie) {
        this.tabelaPodsumowanie = tabelaPodsumowanie;
    }

    // Metoda pobiera pracownika z bazy i wstawia go do listy w aplikacji

    public void pobierzPracownika(String dzial) {
        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.createStatement();
            this.listaPracownik.clear();
            rs = stmt.executeQuery("SELECT * from pracownicy  where dzial ='" + dzial + "'order by pracownik");
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.setId(rs.getInt("id_prac"));
                pracownik.setPracownik(rs.getString("pracownik"));
                this.listaPracownik.add(pracownik);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Błąd SQL pobrania listy pracownikó"+e.getMessage());
        }
    }

    // jak wyżej tylko Dział

    public void pobierzDzial() {
        try {
            conn = ConnectionHelper.getConnection();
            stmt = conn.createStatement();
            this.listaDzial.clear();
            rs = stmt.executeQuery("SELECT dzial from pracownicy group by dzial");
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.setDzial(rs.getString("dzial"));
                this.listaDzial.add(pracownik);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Błąd SQL Exception pobierz dział" + e.getMessage());
        }
    }

    public void pobierzTabele(String pracownik, LocalDate dataStart, LocalDate dataKoniec) {
        godzin = 0;
        minut = 0;
        conn = ConnectionHelper.getConnection();
        tabelCzas.clear();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT pracownik, dzial, data_wejscie, wejscie, wyjscie, stawka, nieobecnosc FROM czas_pracy.godziny c " +
                    "left join pracownicy p on id_pracownik =  p.id_prac where pracownik ='" + pracownik + "' AND data_wejscie between '" +
                    dataStart + "'and'" + dataKoniec + "'order by data_wejscie");
            while (rs.next()) {
                CzasPracy czasPracy = new CzasPracy();
                czasPracy.setPracownik(rs.getString("pracownik"));
                czasPracy.setDzial(rs.getString("dzial"));
                czasPracy.setData_wejscie(rs.getDate("data_wejscie").toLocalDate());
                czasPracy.setWejscie(rs.getTime("wejscie").toLocalTime());
                czasPracy.setWyjscie(rs.getTime("wyjscie").toLocalTime());
                czasPracy.sumaGodzin();
                czasPracy.setStawka(rs.getString("stawka"));
                czasPracy.setNieobecnosc(rs.getString("nieobecnosc"));
                tabelCzas.add(czasPracy);
                godzin = godzin + czasPracy.getSuma().getHour();
                minut = minut + czasPracy.getSuma().getMinute();
            }
            zamianaMinut();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            AlertDialog.butonEdytuj();
        }
    }
    public void podsumowanieMiesiaca(String dzial, LocalDate dataStart, LocalDate dataKoniec){
        conn = ConnectionHelper.getConnection();
        tabelaPodsumowanie.clear();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PRACOWNIK, sec_to_time(sum(TIME_TO_SEC(czas_pracy))) as suma FROM czas_pracy.godziny " +
                    "left join pracownicy p on id_pracownik =  p.id_prac where dzial ='"+dzial+"'and data_wejscie between '"+dataStart+"' and '"+dataKoniec+"'" +
                    " GROUP BY PRACOWNIK ORDER BY PRACOWNIK\n");
            while (rs.next()){
                CzasPracy czasPracy = new CzasPracy();
                czasPracy.setPracownik(rs.getString("pracownik"));
                czasPracy.setPodsumowanie(rs.getString("suma"));
                czasPracy.setPodsumowanie(czasPracy.getPodsumowanie().substring(0,czasPracy.getPodsumowanie().length()-3));
                tabelaPodsumowanie.add(czasPracy);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void zamianaMinut() {
        while (minut >= 60) {
            minut = minut - 60;
            godzin = godzin + 1;
        }
    }
}

