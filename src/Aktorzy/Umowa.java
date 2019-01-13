package Aktorzy;

import Produkt.Produkt;

import java.util.Random;

public class Umowa {
    private int ryczalt;
    private Produkt produkt;
    static private Random rand = new Random();

    public Umowa(Produkt produkt) {
        synchronized(rand) {
            int jakoscProduktu = produkt.getJakosc();
            this.ryczalt = jakoscProduktu * 100 + rand.nextInt(jakoscProduktu * 50);
        }
        this.produkt = produkt;
    }

    public int getRyczalt() {
        return ryczalt;
    }

    public void setRyczalt(int ryczalt) {
        this.ryczalt = ryczalt;
    }

    public Produkt getProdukt() {
        return produkt;
    }
}
