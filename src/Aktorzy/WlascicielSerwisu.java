package Aktorzy;

import Produkt.Produkt;

import java.io.Serializable;

public class WlascicielSerwisu extends Osoba implements Serializable {
    static Integer kwota_startowa = 10000;
    volatile Integer podsumowanieMiesiaca = 0;
    volatile Integer zarobkiWOstanimMiesiacu = 0;


    public WlascicielSerwisu() {
        super(new KontoBankowe(kwota_startowa));
    }

    /**
     * Egzekwuje wszystkie umowy. Jesli jest to umowa wlasciciela z dystrybutorem traci pieniadze,
     * jesli klienta z wlascicielem zyskuje.
     */
    @Override
    public void wyegzekwujUmowy() {
        Integer stanKontaPrzed = super.getKontoBankowe().getStanKonta();
        synchronized (super.getUmowyLock()) {
            for (Umowa umowa : super.getUmowy()) {
                umowa.wyegzekwuj();
            }
        }

        Integer stanKontaPo = super.getKontoBankowe().getStanKonta();
        podsumowanieMiesiaca = stanKontaPo - stanKontaPrzed;
    }

    public Integer getWydatkiWOstanimMiesiacu() {
        return podsumowanieMiesiaca;
    }
}
