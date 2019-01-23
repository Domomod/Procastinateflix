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
    private volatile boolean endAllthread = false;
    private int gorneOgraniczenie = 300;

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

    public void endAllThreadsInNexCycle() {
        endAllthread = true;
    }

    synchronized void wyegzekwujUmowy(){
        for (Klient klient:
             klienci) {
            klient.wyegzekwujUmowy();
        }
    }

    synchronized public void dodajKlienta() {
        Klient nowyKlient = new Klient();
        klienci.add(nowyKlient);
        Thread newThread = new Thread(nowyKlient);
        newThread.start();
    }

    synchronized public Klient wylosujKlienta() {

        return klienci.get(rand.nextInt(klienci.size()));
    }

    synchronized public void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        ArrayList<Klient> tempKlienci = (ArrayList<Klient>)stream.readObject();
        klienci.clear();
        klienci.addAll(tempKlienci);
    }
    synchronized public void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        ArrayList<Klient> temp = new ArrayList<Klient>(klienci);
    }


    public ObservableList<Klient> getKlienci() {
        return klienci;
    }
}
