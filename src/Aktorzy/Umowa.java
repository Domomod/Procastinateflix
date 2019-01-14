package Aktorzy;

import Produkt.Produkt;
import sample.Main;

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

    static public void podpiszAbonamentPremium(Klient klient){
        Umowa abonament = new Umowa(30, klient.getKontoBankowe(), Main.getGracz().getKontoBankowe());
        klient.setAbonament(abonament);
    }

    static public void podpiszZwyklyAbonament(Klient klient){
        Umowa abonament = new Umowa(15, klient.getKontoBankowe(), Main.getGracz().getKontoBankowe());
        klient.setAbonament(abonament);
    }

    public void setRyczalt(int ryczalt) {
        this.ryczalt = ryczalt;
    }

}
