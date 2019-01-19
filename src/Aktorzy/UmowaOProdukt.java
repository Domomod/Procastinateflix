package Aktorzy;

import Produkt.Produkt;

public class UmowaOProdukt extends Umowa {
    private Produkt produkt;

    public UmowaOProdukt(int ryczalt, Osoba platnik, Osoba odbiorca, String nazwa, Produkt produkt) {
        super(ryczalt, platnik, odbiorca, nazwa);
        this.produkt = produkt;
    }
}
