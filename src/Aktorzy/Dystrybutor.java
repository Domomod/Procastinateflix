package Aktorzy;

import Produkt.Generatory.GeneratorFirmowychNazw;
import Produkt.Generatory.GeneratorNazw;
import Produkt.Produkt;
import Produkt.Film;
import Produkt.Serial;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import sample.Main;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import static java.lang.Thread.sleep;

public class Dystrybutor extends Osoba implements Runnable, Serializable {
    private String nazwa;
    private static final Random rand = new Random();
    private List<Produkt> udostepnianeProdukty = new ArrayList<>();

    public Dystrybutor() {
        this.nazwa = GeneratorFirmowychNazw.wygenerujNazwe();
    }

    /**
     * Wytworz nowy produkt i zaproponuj umowe wlascicielowi serwisu.
     */
    public void zaproponujUmowe() {
        Dystrybutor dystrybutor = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Produkt nowyProdukt = wydajProdukt();

                int jakoscProduktu = nowyProdukt.getJakosc();

                int ryczalt;
                synchronized (rand) {
                    ryczalt = jakoscProduktu * 100 + rand.nextInt(jakoscProduktu * 50);
                }

                String opisWiadomości = nowyProdukt.getClass().getSimpleName() + ": " + nowyProdukt.getNazwa() + ", jakosc: " + nowyProdukt.getJakosc() + " za jedyne " + ryczalt + " zł miesięcznie. ";
                Alert alert = new Alert(Alert.AlertType.NONE, opisWiadomości, ButtonType.YES, ButtonType.NO);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setTitle( "Firma "+ nazwa +" chce podpisac nową umowę");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    UmowaOProdukt.podpiszUmowe( dystrybutor, nowyProdukt, ryczalt, SimulationAPI.getWlascicielSerwisu());
                    SimulationAPI.dodajProdukt(nowyProdukt);
                    udostepnianeProdukty.add(nowyProdukt);
                }
            }
        });
    }

    /**
     * Usuwa umowe, jesli była to umowa o produkt usuwa tez produkt z listy oferowanych produktow
     *
     * @param umowa umowa do usuniecia
     */
    @Override
    public void removeUmowy(Umowa umowa) {
        super.removeUmowy(umowa);
        if(umowa instanceof  UmowaOProdukt) {
            UmowaOProdukt umowaOProdukt = (UmowaOProdukt)umowa;
            Produkt produkt = umowaOProdukt.getProdukt();
            this.udostepnianeProdukty.remove(produkt);
        }
    }

    /**
     * Tworzy nowy produkt
     *
     * @return nowy serial lub film
     */
    private Produkt wydajProdukt(){

        int coWyprodukowac = (int)(Math.random() * 2);

        Produkt nowyProdukt;
        if(coWyprodukowac == 0)
            nowyProdukt = new Serial();
        else
            nowyProdukt = new Film();
        return nowyProdukt;
    }

    /**
     * Watek dystrybutora odpowiedzialn za okresowe proponowanie nowych umow.
     */
    @Override
    public void run() {
        while(!ZbiorDystrybutorow.isEndAllthread()) {
            try {
                sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (rand){
                if(rand.nextInt(200)<2)
                    zaproponujUmowe();
            }
        }
    }

    public String getNazwa() {
        return nazwa;
    }
}
