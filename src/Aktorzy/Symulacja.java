package Aktorzy;

import Produkt.Generatory.MiesiacRok;
import Produkt.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import sample.Controller;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 *
 */
public class Symulacja implements Runnable, Serializable {
    private WlascicielSerwisu wlascicielSerwisu = new WlascicielSerwisu();
    transient private ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    private ZbiorDystrybutorow dystrybutorzy = new ZbiorDystrybutorow();
    private ZbiorKlientow klienci = new ZbiorKlientow();
    private boolean endThread = false;
    private int maxZadluzenie = -10000;

    private static OnChangeListener<WlascicielSerwisu> onWlascicielChangeListener;
    public static boolean isControllerCreated = false;

    public Symulacja() {
    }

    /**
     * Dodaje nowy produkt wewnatrz klasy Symulacja
     *
     * @param produkt
     */
    public void dodajProdukt(Produkt produkt) {
        synchronized (this){
            getProdukty().add(produkt);
        }

    }


    /**
     * Usuwa produkt wewnatrz klasy Sumlacja
     *
     * @param produkt
     */
    public void usunProdukt(Produkt produkt) {
        synchronized (this){
            produkty.removeAll(produkt);
        }

    }

    /**
     * Watek symulacji. W jego ramach egzekwowane sa zawarte w syumulacji umowy.
     * Aktualizowany jest wyswietlany stan wlasciciela.
     * Oraz symulowany jest uplyw czasu
     */
    @Override
    public void run() {

        Thread watekDystrybutorow = new Thread(dystrybutorzy);
        watekDystrybutorow.start();

        Thread watekKlientow = new Thread(klienci);
        watekKlientow.start();


        while (!endThread) {
            try {
                sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            wlascicielSerwisu.wyegzekwujUmowy();

            synchronized (this){
                for (Produkt produkt : produkty) {
                    produkt.zaktualizujWykres();
                }
            }

            if (isControllerCreated){
                onWlascicielChangeListener.onChange(wlascicielSerwisu);
            }

            if(wlascicielSerwisu.getKontoBankowe().getStanKonta() < -maxZadluzenie) {
                String opisWiadomości = "DB - Właściciel serwisu VOD 'ProcastinateFlix' ogłasze bankructwo. Pogłoski mówią" +
                        "że w czasie działalności dopuścił się wielu przestępstw podatkowych. Sprawą zajmuje się prokuratura.";
                Alert alert = new Alert(Alert.AlertType.NONE, opisWiadomości, ButtonType.YES, ButtonType.NO);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setTitle( "Breaking News");
                alert.showAndWait();
                endThreadInNextCycle();
            }

            MiesiacRok.miesiacMinal();


        }
    }

    /**
     * @return zwraca losowy Produkt znajdujacy sie juz wewnatrz symulacji (nie nowy wygenerowany)
     * @throws BrakProduktowException wyjatek w wypadku gdy w symulacji nie ma zadnych produktow
     */
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

    /**
     * Ustawia flage dzięki ktorej funkcja run przy następnej iteracji zakonczy swoje działanie
     */
    public void endThreadInNextCycle() {
        klienci.endAllThreadsInNexCycle();
        dystrybutorzy.endAllThreadsInNexCycle();
        this.endThread = true;
    }

    public static void setOnWlascicielChangeListener(OnChangeListener<WlascicielSerwisu> onWlascicielChangeListener) {
        Symulacja.onWlascicielChangeListener = onWlascicielChangeListener;
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Produkt> temp = (ArrayList<Produkt>)stream.readObject();
        produkty = FXCollections.observableArrayList(temp);
        Umowa.setRyczaltAbonamentuZwyklego((Integer)stream.readObject());
        Umowa.setRyczaltAbonamentuPremium((Integer)stream.readObject());
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Produkt>(produkty));
        stream.writeObject(Umowa.getRyczaltAbonamentuZwyklego());
        stream.writeObject(Umowa.getRyczaltAbonamentuPremium());

    }
}