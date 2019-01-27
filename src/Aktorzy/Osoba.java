package Aktorzy;

import Produkt.Produkt;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Osoba implements Serializable {
    private KontoBankowe kontoBankowe = new KontoBankowe();
    transient private ObservableList<Umowa> umowy = FXCollections.observableArrayList();
    private Object umowyLock = new Object();

    public Osoba() {
    }

    public Osoba(KontoBankowe kontoBankowe) {
        this.kontoBankowe = kontoBankowe;
        this.umowy = FXCollections.observableArrayList();
    }

    public KontoBankowe getKontoBankowe() {

        return kontoBankowe;
    }

    public void wyegzekwujUmowy() {
        for (Umowa umowa : umowy) {
            umowa.wyegzekwuj();

        }
    }

    public void addUmowa(Umowa umowa) {
        synchronized (umowyLock) {
            Osoba osoba = this;
            Platform.runLater(()->{
                osoba.umowy.add(umowa);
            });
        }

    }

    public void removeUmowy(Umowa umowa) {
        synchronized (umowyLock) {
            Osoba osoba = this;
            Platform.runLater(()->{
                osoba.umowy.remove(umowa);
            });
        }

    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Umowa> temp = (ArrayList<Umowa>)stream.readObject();
        umowy = FXCollections.observableArrayList(temp);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Umowa>(umowy));
    }

    public void removeUmowy(ObservableList<Umowa> umowy) {
        Osoba osoba = this;
        Platform.runLater(()->{
            synchronized (umowyLock) {
                osoba.umowy.removeAll(umowy);
            }
        });
    }

    public ObservableList<Umowa> getUmowy() {
        return umowy;
    }

    public Object getUmowyLock() {
        return umowyLock;
    }
}
