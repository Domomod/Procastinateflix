package Aktorzy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ZbiorDystrybutorow extends Thread implements Serializable {
    transient private ObservableList<Dystrybutor> dystrybutorzy = FXCollections.observableArrayList();
    private Random rand = new Random();
    private volatile boolean endAllthread = false;
    private int gorneOgraniczenie = 15;

    public void run() {
        while (!endAllthread) {

            if (rand.nextInt(100) < 2 && dystrybutorzy.size() < gorneOgraniczenie) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        dodajDystrybutora();
                    }
                });
            }


            try {
                sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void nowyDystrybutorWiadomosc() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wzrośnie liczba proponowanych umów");
                alert.setTitle("Nowy dystybutor chce współpracować");
                alert.showAndWait();
            }
        });
    }

    public void endAllThreadsInNexCycle() {
        endAllthread = true;
    }

    public void dodajDystrybutora() {
        Dystrybutor nowyDystrybutor = new Dystrybutor();
        dystrybutorzy.add(nowyDystrybutor);
        nowyDystrybutorWiadomosc();
        Thread newThread = new Thread(nowyDystrybutor);
        newThread.start();
    }

    public Dystrybutor wylosujDystrybutora() {

        return dystrybutorzy.get(rand.nextInt(dystrybutorzy.size()));
    }

    public void wygenerujNowyProduktNaZlecenieUżytkownika() {

        if (dystrybutorzy.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Czy chcesz rozpozcac negocjacje z kims nowym?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle("Nie ma dystrybutorow");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                dodajDystrybutora();
            }
        }

        Dystrybutor dystrybutor = dystrybutorzy.get(rand.nextInt(dystrybutorzy.size()));
        dystrybutor.zaproponujUmowe();
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Dystrybutor> tempDystrybutorzy = (ArrayList<Dystrybutor>)stream.readObject();
        dystrybutorzy = FXCollections.observableArrayList(tempDystrybutorzy);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Dystrybutor>(dystrybutorzy));
    }

    public ObservableList<Dystrybutor> getDystrybutorzy() {
        return dystrybutorzy;
    }
}
