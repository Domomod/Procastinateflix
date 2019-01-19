package Aktorzy;

import Produkt.Generatory.MiesiacRok;
import Produkt.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Controller;

import java.util.Map;

import static java.lang.Thread.sleep;

public class Symulacja implements Runnable {
    private WlascicielSerwisu wlascicielSerwisu = new WlascicielSerwisu();
    private ObservableList<Produkt> produkty = FXCollections.observableArrayList();

    private ZbiorDystrybutorow dystrybutorzy = new ZbiorDystrybutorow();
    private ZbiorKlientow klienci = new ZbiorKlientow();
    private boolean endThread = false;
    private static OnChangeListener<WlascicielSerwisu> onWlascicielChangeListener;
    static public Controller kontroler;
    public static boolean isControllerCreated = false;

    public Symulacja() {
    }

    public void dodajProdukt(Produkt produkt) {
        getProdukty().add(produkt);
    }

    @Override
    public void run() {

        Thread watekDystrybutorow = new Thread(dystrybutorzy);
        watekDystrybutorow.start();

        Thread watekKlientow = new Thread(klienci);
        watekKlientow.start();


        while (true) {
            try {
                sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            wlascicielSerwisu.wyegzekwujUmowy();

            for (Produkt produkt : produkty) {
                produkt.zaktualizujWykres();
            }

            if (isControllerCreated){
                onWlascicielChangeListener.onChange(wlascicielSerwisu);
            }

            MiesiacRok.miesiacMinal();
        }

        //klienci.endAllThreadsInNexCycle();
        //dystrybutorzy.endAllThreadsInNexCycle();

    }

    public Produkt getLosowyProdukt() throws  BrakProduktowException{
        if(produkty.isEmpty())
            throw new BrakProduktowException();
        return produkty.get((int) (Math.random() * produkty.size()));
    }

    public class BrakProduktowException extends Throwable {

    }

    public ObservableList<Produkt> getProdukty() {
        return produkty;
    }

    public ZbiorDystrybutorow getDystrybutorzy() {
        return dystrybutorzy;
    }

    public ZbiorKlientow getKlienci() {
        return klienci;
    }

    public WlascicielSerwisu getWlascicielSerwisu() {
        return wlascicielSerwisu;
    }

    public void endThreadInNextCycle(boolean endThread) {
        this.endThread = endThread;
    }

    public static void setOnWlascicielChangeListener(OnChangeListener<WlascicielSerwisu> onWlascicielChangeListener) {
        Symulacja.onWlascicielChangeListener = onWlascicielChangeListener;
    }
}