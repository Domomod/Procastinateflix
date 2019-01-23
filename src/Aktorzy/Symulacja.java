package Aktorzy;

import Produkt.Generatory.MiesiacRok;
import Produkt.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.Controller;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Symulacja implements Runnable, Serializable {
    private WlascicielSerwisu wlascicielSerwisu = new WlascicielSerwisu();
    transient private ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    private ZbiorDystrybutorow dystrybutorzy = new ZbiorDystrybutorow();
    private ZbiorKlientow klienci = new ZbiorKlientow();
    private boolean endThread = false;

    private static OnChangeListener<WlascicielSerwisu> onWlascicielChangeListener;
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

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Produkt> temp = (ArrayList<Produkt>)stream.readObject();
        produkty = FXCollections.observableArrayList(temp);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Produkt>(produkty));

    }

    public void setWlascicielSerwisu(WlascicielSerwisu wlascicielSerwisu) {
        this.wlascicielSerwisu = wlascicielSerwisu;
    }

    public void setProdukty(ObservableList<Produkt> produkty) {
        this.produkty = produkty;
    }

    public void setDystrybutorzy(ZbiorDystrybutorow dystrybutorzy) {
        this.dystrybutorzy = dystrybutorzy;
    }

    public void setKlienci(ZbiorKlientow klienci) {
        this.klienci = klienci;
    }

    public boolean isEndThread() {
        return endThread;
    }

    public void setEndThread(boolean endThread) {
        this.endThread = endThread;
    }

    public static OnChangeListener<WlascicielSerwisu> getOnWlascicielChangeListener() {
        return onWlascicielChangeListener;
    }

    public static boolean isIsControllerCreated() {
        return isControllerCreated;
    }

    public static void setIsControllerCreated(boolean isControllerCreated) {
        Symulacja.isControllerCreated = isControllerCreated;
    }
}