package Aktorzy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Osoba {
    private KontoBankowe kontoBankowe;
    private ObservableList<Umowa> umowy;

    public Osoba() {
        this.kontoBankowe = new KontoBankowe();
        this.umowy = FXCollections.observableArrayList();
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
        Osoba osoba = this;
        Platform.runLater(()->{
            osoba.umowy.add(umowa);
        });
    }

    public void removeUmowy(Umowa umowa) {
        Osoba osoba = this;
        Platform.runLater(()->{
            osoba.umowy.remove(umowa);
        });
    }

    public void removeUmowy(ObservableList<Umowa> umowy) {
        this.umowy.removeAll(umowy);
    }

    public ObservableList<Umowa> getUmowy() {
        return umowy;
    }
}
