package Produkt;

import Aktorzy.Umowa;
import Produkt.Generatory.GeneratorImionNazwisk;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Film extends ProduktObjetyAbonamentem {
    transient private ObservableList<String> aktorzy;
    private Set<URL> linkiDoZwiastunow;
    private SimpleDateFormat terminDoOstatniegoObejrzenia;
    private String gatunek;


    public Film() {
        super();
        aktorzy = FXCollections.observableArrayList();
        for(int i = 0; i < 20; i++){
            aktorzy.add(GeneratorImionNazwisk.wygenerujNazwe());
        }

        super.appendOpis(" W filmie występują znane gwizay, jak " + aktorzy.get(0) + " lub " + aktorzy.get(1) + ".");

        linkiDoZwiastunow = new TreeSet<>();
    }

    public Set<URL> getLinkiDoZwiastunow() {
        return linkiDoZwiastunow;
    }


    public SimpleDateFormat getTerminDoOstatniegoObejrzenia() {
        return terminDoOstatniegoObejrzenia;
    }


    public String getGatunek() {
        return gatunek;
    }

    public ObservableList<String> getAktorzy() {
        return aktorzy;
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        ArrayList<String> temp = (ArrayList<String>)stream.readObject();
        aktorzy = FXCollections.observableArrayList(temp);
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(new ArrayList<String>(aktorzy));
    }
}

