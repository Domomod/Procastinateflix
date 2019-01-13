package Aktorzy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Random;

public class ZbiorDystrybutorow extends Thread{
    static private ObservableList<Dystrybutor> dystrybutorzy = FXCollections.observableArrayList();
    static private Random rand = new Random();
    static private volatile boolean endAllthread = false;
    static private int gorneOgraniczenie = 15;

    public void run(){
        while(!endAllthread){

            if(rand.nextInt(100)<2 && dystrybutorzy.size() < 5) {
                dodajDystrybutora();
            }


            try {
                sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static private void nowyDystrybutorWiadomosc() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Wzrośnie liczba proponowanych umów");
                alert.setTitle("Nowy dystybutor chce współpracować");
                alert.showAndWait();
            }
        });
    }

    static public void endAllThreadsInNexCycle(){
        endAllthread = true;
    }

    synchronized static public void dodajDystrybutora(){
        Dystrybutor nowyDystrybutor = new Dystrybutor();
        dystrybutorzy.add(nowyDystrybutor);
        nowyDystrybutorWiadomosc();
        Thread newThread = new Thread(nowyDystrybutor);
        newThread.start();
    }

    synchronized static public Dystrybutor wylosujDystrybutora(){

        return dystrybutorzy.get(rand.nextInt(dystrybutorzy.size()));
    }

    synchronized static public void wygenerujNowyProduktNaZlecenieUżytkownika(){

        if(dystrybutorzy.isEmpty()){
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
}
