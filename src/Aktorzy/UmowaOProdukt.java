package Aktorzy;

import Produkt.Produkt;

public class UmowaOProdukt extends Umowa {
    private Produkt produkt;

    public UmowaOProdukt(int ryczalt, KontoBankowe platnik, KontoBankowe odbiorca, Produkt produkt) {
        super(ryczalt, platnik, odbiorca);
        this.produkt = produkt;
    }
}
