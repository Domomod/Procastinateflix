package Aktorzy;

import Produkt.Produkt;

import java.util.Random;

public class Umowa {
    private int ryczalt;
    private KontoBankowe platnik;
    private KontoBankowe odbiorca;

    public Umowa(int ryczalt, KontoBankowe platnik, KontoBankowe odbiorca) {
        this.ryczalt = ryczalt;
        this.platnik = platnik;
        this.odbiorca = odbiorca;
    }

    public void wyegzekwuj() {
        try {
            platnik.przelejSrodkiNa(ryczalt, odbiorca);
        } catch (KontoBankowe.NiewystarczajacaLiczbaSrodkowException e) {
            e.printStackTrace();
        }
    }

    public int getRyczalt() {
        return ryczalt;
    }

    public void setRyczalt(int ryczalt) {
        this.ryczalt = ryczalt;
    }

}
