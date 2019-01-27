package Produkt;

import Aktorzy.Umowa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sezon implements Serializable {
    transient private ObservableList<Odcinek> odcinki;
    private String nazwa;

    /**
     * Konstruktor klasy Sezon, liczba odcinków jest losowa.
     * @param nazwa Nazwa sezonu
     */
    public Sezon(String nazwa){
        this.nazwa = nazwa;
        odcinki = FXCollections.observableArrayList();
        int liczbaOdc = 5 + (int)( Math.random() * 5);
        for(int i = 0;  i < liczbaOdc; i ++){
            Odcinek nowy = new Odcinek("Odcinek " + i);
            odcinki.add(nowy);
        }
    }

    public String getNazwa() {
        return nazwa;
    }

    public ObservableList<Odcinek> getOdcinki() {
        return odcinki;
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Odcinek> temp = (ArrayList<Odcinek>)stream.readObject();
        odcinki = FXCollections.observableArrayList(temp);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Odcinek>(odcinki));
    }
}
