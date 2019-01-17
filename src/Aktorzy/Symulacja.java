package Aktorzy;

import Produkt.Produkt;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Symulacja implements Runnable {
    private WlascicielSerwisu wlascicielSerwisu = new WlascicielSerwisu();
    private ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    private ObservableMap<Produkt, Umowa> umowy = FXCollections.observableHashMap();
    private ZbiorDystrybutorow dystrybutorzy = new ZbiorDystrybutorow();
    private boolean endThread = false;
    private static OnChangeListener onChangeListener;
    static public Controller kontroler;
    public static boolean isControllerCreated = false;

    public Symulacja() {
    }

    public void dodajProdukt(Produkt produkt, Umowa umowa) {
        getProdukty().add(produkt);
        getUmowy().put(produkt, umowa);
    }

    @Override
    public void run() {

        //Thread watekDystrybutorow = new Thread(dystrybutorzy);
        //watekDystrybutorow.start();

        while (true) {
            try {
                sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Map.Entry<Produkt, Umowa> element : umowy.entrySet()) {
                Umowa umowa = element.getValue();
                umowa.wyegzekwuj();
            }

            System.out.println("chchch");
            if (isControllerCreated)
                onChangeListener.onChange(wlascicielSerwisu);
        }

    }

    public Produkt getLosowyProdukt() {
        return produkty.get((int) (Math.random() * produkty.size()));
    }

    public ObservableList<Produkt> getProdukty() {
        return produkty;
    }

    public ObservableMap<Produkt, Umowa> getUmowy() {
        return umowy;
    }

    public ZbiorDystrybutorow getDystrybutorzy() {
        return dystrybutorzy;
    }

    public WlascicielSerwisu getWlascicielSerwisu() {
        return wlascicielSerwisu;
    }

    public void endThreadInNextCycle(boolean endThread) {
        this.endThread = endThread;
    }

    public static void setOnChangeListener(OnChangeListener onChangeListener) {
        Symulacja.onChangeListener = onChangeListener;
    }
}