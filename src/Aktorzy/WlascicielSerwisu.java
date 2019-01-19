package Aktorzy;

import Produkt.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Map;

public class WlascicielSerwisu extends Osoba{
    static Integer kwota_startowa = 10000;
    volatile Integer podsumowanieMiesiaca = 0;
    volatile Integer zarobkiWOstanimMiesiacu = 0;


    public WlascicielSerwisu() {
        super(new KontoBankowe(kwota_startowa));
    }

    @Override
    public void wyegzekwujUmowy() {
        Integer stanKontaPrzed = super.getKontoBankowe().getStanKonta();
        for (Umowa umowa : super.getUmowy()) {
            umowa.wyegzekwuj();

        }
        Integer stanKontaPo = super.getKontoBankowe().getStanKonta();
        podsumowanieMiesiaca = stanKontaPo - stanKontaPrzed;
    }

    public Integer getWydatkiWOstanimMiesiacu() {
        return podsumowanieMiesiaca;
    }
}
