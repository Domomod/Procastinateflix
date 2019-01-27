package Aktorzy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ZbiorKlientow extends Thread implements Serializable {
    transient private ObservableList<Klient> klienci = FXCollections.observableArrayList();
    private Random rand = new Random();
    private static volatile boolean endAllthread = false;
    private int gorneOgraniczenie = 300;

    /**
     * Uruchamia watek zarzadzajacy tworzeniem nowych klientow
     */
    public void run() {
        while (!endAllthread) {
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (rand.nextInt(50) < 2 && klienci.size() < gorneOgraniczenie) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this.getClass()) {
                            dodajKlienta();
                        }
                    }
                });
            }
        }
    }

    /**
     * Ustawia flage dzięki ktorej funkcja run przy następnej iteracji zakonczy swoje działanie
     */
    public void endAllThreadsInNexCycle() {
        endAllthread = true;
    }

    /**
     * Dodaje nowego losowo wygenerowanego klienta.  Watek klienta zostaje automatyzcnie uruchomiony
     */
    synchronized public void dodajKlienta() {
        Klient nowyKlient = new Klient();
        klienci.add(nowyKlient);
        Thread newThread = new Thread(nowyKlient);
        newThread.start();
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Klient> tempKlienci = (ArrayList<Klient>)stream.readObject();
        klienci = FXCollections.observableArrayList(tempKlienci);
    }
    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Klient>(klienci));
    }


    public ObservableList<Klient> getKlienci() {
        return klienci;
    }

    public static boolean isEndAllthread() {
        return endAllthread;
    }
}
