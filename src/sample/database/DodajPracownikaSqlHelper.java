package sample.database;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import sample.alert.AlertDialog;
        import sample.modul.Pracownik;
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;

public class DodajPracownikaSqlHelper {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private ObservableList<Pracownik> tabelPracownicy = FXCollections.observableArrayList();

    public ObservableList<Pracownik> getTabelPracownicy() {
        return tabelPracownicy;
    }

    public void setTabelPracownicy(ObservableList<Pracownik> tabelPracownicy) {
        this.tabelPracownicy = tabelPracownicy;
    }

    public void szukajPracownika(String tekst) {
        tabelPracownicy.clear();
        conn = ConnectionHelper.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id_prac, pracownik, dzial FROM PRACOWNICY WHERE PRACOWNIK LIKE '%" + tekst + "%'");
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.setPracownik(rs.getString("pracownik"));
                pracownik.setId(rs.getInt("id_prac"));
                pracownik.setDzial(rs.getString("dzial"));
                tabelPracownicy.add(pracownik);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Nie ma");
        }
    }

    public void edytujPracownika(String pracownik, String dzial, String id) {
        conn = ConnectionHelper.getConnection();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("update pracownicy set pracownik = '" + pracownik + "', dzial = '" + dzial + "' WHERE id_prac ='" + id + "'");
            System.out.println("Pracownik edytowany");
            AlertDialog.edytowanyPracownik();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean sprawdzCzyJestPracownik(String pracownik) {
        boolean pracownikWpisany = false;
        conn = ConnectionHelper.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT pracownik FROM pracownicy where pracownik ='" + pracownik + "'");
            if (rs.next()) {
                pracownikWpisany = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pracownikWpisany;
    }

    public void dodajPracownika(String pracownik, String dzial) {
        if (sprawdzCzyJestPracownik(pracownik)) {
            AlertDialog.pracownikWpisany();
        } else {
            conn = ConnectionHelper.getConnection();
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO pracownicy (pracownik, dzial) values ('" + pracownik.toUpperCase() + "','" + dzial.toUpperCase() + "')");
                System.out.println("Pracownik dodany");
                AlertDialog.dodanyPracownik();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
