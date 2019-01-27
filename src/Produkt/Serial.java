package Produkt;

import Aktorzy.Umowa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Serial extends ProduktObjetyAbonamentem {
    transient private ObservableList<Sezon> sezony;
    private String gatunek;

    /**
     * Konstruktor klasy Serial, nowa klasa jest wygenerowana w dużym stopniu w sposób losowy
     */
    public Serial() {
        super();

        sezony = FXCollections.observableArrayList();
        int liczbaSezonow = 5 + (int)( Math.random() * 5);
        for(int i = 0;  i < liczbaSezonow; i ++){
            Sezon nowy = new Sezon("Sezon " + i);
            sezony.add(nowy);
        }

        super.appendOpis("Przygotuj się na " + liczbaSezonow + " sezonów które wbiją cię w fotel. Obiecujemy że każdy z odcinków sprawi że powiesz \" jeszcze jeden odcineczek zanim zaczne robotę. \"");
    }

    public ObservableList<Sezon> getSezony() {
        return sezony;
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<Sezon> temp = (ArrayList<Sezon>)stream.readObject();
        sezony = FXCollections.observableArrayList(temp);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<Sezon>(sezony));
    }
}

