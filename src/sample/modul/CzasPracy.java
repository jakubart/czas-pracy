package sample.modul;


import java.time.LocalDate;
import java.time.LocalTime;

public class CzasPracy {

    private String pracownik;
    private String dzial;
    private LocalDate data_wejscie;
    private LocalTime wejscie;
    private LocalTime wyjscie;
    private LocalTime suma;
    private String nieobecnosc;
    private String stawka;
    private String podsumowanie;

    public CzasPracy() {
    }

    public String getPracownik() {
        return pracownik;
    }

    public void setPracownik(String pracownik) {
        this.pracownik = pracownik;
    }

    public String getDzial() {
        return dzial;
    }

    public void setDzial(String dzial) {
        this.dzial = dzial;
    }

    public LocalDate getData_wejscie() {
        return data_wejscie;
    }

    public void setData_wejscie(LocalDate data_wejscie) {
        this.data_wejscie = data_wejscie;
    }

    public LocalTime getWejscie() {
        return wejscie;
    }

    public void setWejscie(LocalTime wejscie) {
        this.wejscie = wejscie;
    }

    public LocalTime getWyjscie() {
        return wyjscie;
    }

    public void setWyjscie(LocalTime wyjscie) {
        this.wyjscie = wyjscie;
    }

    public LocalTime getSuma() {
        return suma;
    }

    public void setSuma(LocalTime suma) {
        this.suma = suma;
    }

    public String getNieobecnosc() {
        return nieobecnosc;
    }

    public void setNieobecnosc(String nieobecnosc) {
        this.nieobecnosc = nieobecnosc;
    }

    public String getPodsumowanie() {
        return podsumowanie;
    }

    public void setPodsumowanie(String podsumowanie) {
        this.podsumowanie = podsumowanie;
    }

    public String getStawka() {
        return stawka;
    }

    public void setStawka(String stawka) {
        this.stawka = stawka;
    }

    public LocalTime sumaGodzin() {
        if (wyjscie.isAfter(wejscie)) {
            if (wejscie.getMinute() < wyjscie.getMinute()) {
                suma = wyjscie.minusHours(wejscie.getHour());
                suma = suma.minusMinutes(wejscie.getMinute());
            } else if (wejscie.getMinute() == wyjscie.getMinute()) {
                suma = wyjscie.minusHours(wejscie.getHour());
            } else {
                suma = wyjscie.minusHours(wejscie.getHour() + 1);
                suma = suma.plusMinutes(60 - wejscie.getMinute());
            }
        } else {
            if (wejscie.getMinute() == wyjscie.getMinute()) {
                suma = wyjscie.plusHours(24 - wejscie.getHour());
            } else if (wejscie.getMinute() > wyjscie.getMinute()) {
                suma = wyjscie.plusHours(24 - wejscie.getHour());
                suma = suma.plusMinutes(60 - wejscie.getMinute());
            } else {
                suma = wyjscie.plusHours(24 - wejscie.getHour());
                suma = suma.minusMinutes(wejscie.getMinute());
            }
        }
        return suma;
    }

  /*  public LocalTime stawka(){
        wejscie.
    }
*/}

