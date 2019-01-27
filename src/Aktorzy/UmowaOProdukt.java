package Aktorzy;

import Produkt.Produkt;

public class UmowaOProdukt extends Umowa {
    private Produkt produkt;

    public UmowaOProdukt(int ryczalt, Osoba platnik, Osoba odbiorca, String nazwa, Produkt produkt) {
        super(ryczalt, platnik, odbiorca, nazwa);
        this.produkt = produkt;
    }

    /**
     * @param dystrybutor dystrybutor na konto ktorego wplywac beda pieniadze
     * @param produkt produkt ktory dystrybutor udostepinia
     * @param ryczalt miesiaczna oplata za udostepniony produkt
     * @param wlasciciel wlasciciel serwisu ktory poniesie koszty umowy
     */
    static public void podpiszUmowe(Dystrybutor dystrybutor, Produkt produkt ,int ryczalt, WlascicielSerwisu wlasciciel){
        UmowaOProdukt nowaUmowa = new UmowaOProdukt(ryczalt, SimulationAPI.getWlascicielSerwisu(), dystrybutor, "Prawa do " + produkt.getNazwa() + " (" + ryczalt + " zl/mies)", produkt);
        wlasciciel.addUmowa(nowaUmowa);
    }


    public Produkt getProdukt() {
        return produkt;
    }
}
