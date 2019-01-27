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

    /**
     * Przelej srodki z konta platnika na konto odbiorcy.
     */
    public void wyegzekwuj() {
        platnik.getKontoBankowe().przelejSrodkiNa(ryczalt, odbiorca.getKontoBankowe());
    }

    public Integer getRyczalt() {
        return ryczalt;
    }

    /**
     * Zerwij umowe, a zatem usun ja z listy umow platnika i odbiorcy.
     */
    public void zerwijUmowe(){
        Platform.runLater(()->{
            platnik.getUmowy().remove(this);
            odbiorca.getUmowy().remove( this);
        });
    }

    /**
     * Zerwij abonament. Jesli umowa nie jest abonamentem zwraca wyjatek.
     */
    public void zerwijAbonament() throws UmowaNieJestAbonamentemException{
        if(platnik instanceof Klient && ((Klient) platnik).getAbonament() == this) {
            ((Klient) platnik).setAbonament(null);
            odbiorca.getUmowy().remove(this);
        } else throw new UmowaNieJestAbonamentemException();
    }

    public class UmowaNieJestAbonamentemException extends RuntimeException{}

    /**
     * Zrywa umowy, zatem usuwa je z list platnika i odbiorcy.
     *
     * @param umowy umowy ktore zostano zerwane
     */
    static public void zerwijUmowy(ObservableList<Umowa> umowy){
        if(umowy.size() <= 0)
            return;
        Osoba platnik = umowy.get(0).getPlatnik();
        Osoba odbiorca = umowy.get(0).getOdbiorca();
        odbiorca.removeUmowy(umowy);
        platnik.removeUmowy(umowy);

    }

    /**
     * Tworzy nowa umowe miedzy klientem(platnikiem) a wlascicielm(odbiorca) i umieszcza je na listach wlasciciela i kienta
     *
     * @param klient klient wykupujacy produkt
     * @param produkt produkt o ktorry umowa jest podpiywana
     * @param wlasciciel wlasciciel serwisu VOD
     */
    static public void podpiszUmowe(Klient klient, Produkt produkt, WlascicielSerwisu wlasciciel){
        Umowa nowaUmowa = new Umowa(produkt.getCena(), klient, SimulationAPI.getWlascicielSerwisu(), klient.getNazwa() + " wypożycza " + produkt.getNazwa());
        klient.addUmowa(nowaUmowa);
        wlasciciel.addUmowa(nowaUmowa);
    }

    /**
     * Tworzy nowa umowe miedzy dystrybutorem(odbiorca) a wlascicelem(platnikiem)
     *
     * @param dystrybutor
     * @param produkt
     * @param ryczalt
     * @param wlasciciel
     */
    static public void podpiszUmowe(Dystrybutor dystrybutor, Produkt produkt ,int ryczalt, WlascicielSerwisu wlasciciel){
        Umowa nowaUmowa = new Umowa(ryczalt, SimulationAPI.getWlascicielSerwisu(), dystrybutor, "Prawa do " + produkt.getNazwa() + " (" + ryczalt + " zl/mies)");
        wlasciciel.addUmowa(nowaUmowa);
    }

    /**
     * Klient wykupuje abonament Premium, jego pozostale umowy zostaja usuniete.
     *
     * @param klient
     * @param wlascicielSerwisu
     */
    static public void podpiszAbonamentPremium(Klient klient, WlascicielSerwisu wlascicielSerwisu){
        Umowa abonament = new Umowa(ryczaltAbonamentuPremium, klient, SimulationAPI.getWlascicielSerwisu(), "Abonament Premium: " + klient.getNazwa());
        klient.setAbonament(abonament);
        wlascicielSerwisu.addUmowa(abonament);
    }

    /**
     * Klient wykupuje abonament Premium, jego pozostale umowy zostaja usuniete.
     *
     * @param klient
     * @param wlascicielSerwisu
     */
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

    /**
     * Zmienia cene abonamentu premium
     *
     * @param ryczaltAbonamentuPremium nowy ryczalt
     */
    public static void setRyczaltAbonamentuPremium(Integer ryczaltAbonamentuPremium) {
        Umowa.ryczaltAbonamentuPremium = ryczaltAbonamentuPremium;
    }

    /**
     * Zmienia cene zwyklego abonamentu
     *
     * @param ryczaltAbonamentuZwyklego nowy ryczalt
     */
    public static void setRyczaltAbonamentuZwyklego(Integer ryczaltAbonamentuZwyklego) {
        Umowa.ryczaltAbonamentuZwyklego = ryczaltAbonamentuZwyklego;
    }
}
