package sample.modul;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pracownik {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty pracownik = new SimpleStringProperty();
    private StringProperty dzial = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getPracownik() {
        return pracownik.get();
    }

    public StringProperty pracownikProperty() {
        return pracownik;
    }

    public void setPracownik(String pracownik) {
        this.pracownik.set(pracownik);
    }

    public String getDzial() {
        return dzial.get();
    }

    public StringProperty dzialProperty() {
        return dzial;
    }

    public void setDzial(String dzial) {
        this.dzial.set(dzial);
    }


    @Override
    public String toString() {
        if (getDzial() == null) {
            return pracownik.getValue();
        } else {
            return dzial.getValue();
        }
    }
}
