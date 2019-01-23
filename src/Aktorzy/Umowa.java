package Aktorzy;

import Produkt.Produkt;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import sample.Main;

import java.io.Serializable;
import java.util.Random;

public class Umowa implements Serializable {
    private Integer ryczalt;
    private String nazwa;
    private Osoba platnik;
    private Osoba odbiorca;
    static private final Random rand = new Random();
    private static Integer ryczaltAbonamentuPremium = 30;
    private static Integer ryczaltAbonamentuZwyklego = 15;

    public Umowa(Integer ryczalt, Osoba platnik, Osoba odbiorca, String nazwa) {
        this.ryczalt = ryczalt;
        this.platnik = platnik;
        this.odbiorca = odbiorca;
        this.nazwa = nazwa;
    }

    public void wyegzekwuj() {
        platnik.getKontoBankowe().przelejSrodkiNa(ryczalt, odbiorca.getKontoBankowe());
    }

    public Integer getRyczalt() {
        return ryczalt;
    }

    public void zerwijUmowe(){
        platnik.getUmowy().remove(this);
        odbiorca.getUmowy().remove( this);
    }

    public void zerwijAbonament() throws UmowaNieJestAbonamentemException{
        if(platnik instanceof Klient && ((Klient) platnik).getAbonament() == this) {
            ((Klient) platnik).setAbonament(null);
            odbiorca.getUmowy().remove(this);
        } else throw new UmowaNieJestAbonamentemException();
    }

    public class UmowaNieJestAbonamentemException extends RuntimeException{}

    static public void zerwijUmowy(ObservableList<Umowa> umowy){
        if(umowy.size() <= 0)
            return;
        Osoba platnik = umowy.get(0).getPlatnik();
        Osoba odbiorca = umowy.get(0).getOdbiorca();
        odbiorca.removeUmowy(umowy);
        platnik.removeUmowy(umowy);

    }

    static public void podpiszUmowe(Klient klient, Produkt produkt, WlascicielSerwisu wlasciciel){
        Umowa nowaUmowa = new Umowa(produkt.getCena(), klient, SimulationAPI.getWlascicielSerwisu(), klient.getNazwa() + " wypożycza " + produkt.getNazwa());
        klient.addUmowa(nowaUmowa);
        wlasciciel.addUmowa(nowaUmowa);
    }

    static public void podpiszUmowe(Dystrybutor dystrybutor, Produkt produkt ,int ryczalt, WlascicielSerwisu wlasciciel){
        Umowa nowaUmowa = new Umowa(ryczalt, SimulationAPI.getWlascicielSerwisu(), dystrybutor, "Prawa do " + produkt.getNazwa() + " (" + ryczalt + " zl/mies)");
        wlasciciel.addUmowa(nowaUmowa);
    }

    static public void podpiszAbonamentPremium(Klient klient, WlascicielSerwisu wlascicielSerwisu){
        Umowa abonament = new Umowa(ryczaltAbonamentuPremium, klient, SimulationAPI.getWlascicielSerwisu(), "Abonament Premium: " + klient.getNazwa());
        klient.setAbonament(abonament);
        wlascicielSerwisu.addUmowa(abonament);
    }

    static public void podpiszZwyklyAbonament(Klient klient, WlascicielSerwisu wlascicielSerwisu){
        Umowa abonament = new Umowa(ryczaltAbonamentuZwyklego, klient, SimulationAPI.getWlascicielSerwisu(), "Abonament: " + klient.getNazwa());
        klient.setAbonament(abonament);
        wlascicielSerwisu.addUmowa(abonament);
    }

    public Osoba getPlatnik() {
        return platnik;
    }

    public Osoba getOdbiorca() {
        return odbiorca;
    }

    public void setRyczalt(Integer ryczalt) {
        this.ryczalt = ryczalt;
    }

    public String getNazwa() {
        return nazwa;
    }

    public static Integer getRyczaltAbonamentuPremium() {
        return ryczaltAbonamentuPremium;
    }

    public static Integer getRyczaltAbonamentuZwyklego() {
        return ryczaltAbonamentuZwyklego;
    }
}
