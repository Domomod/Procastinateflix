package Aktorzy;

import Produkt.Generatory.GeneratorNazw;
import Produkt.Produkt;
import Produkt.Film;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Dystrybutor {
    private String nazwa;
    private KontoBankowe kontoBankowe;


    public void zaproponujUmowe() {
        try {
            Main.noweOknoPropozycji();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Produkt wydajProdukt(){
        Duration czasTrwania = Duration.ZERO;
        czasTrwania.plusHours( 1 );
        czasTrwania.plusMinutes( 45 );
        Set<String> aktorzy = new TreeSet<>();
        aktorzy.add("Jacek Piotrowiak");
        aktorzy.add("MaFXMLciek Borowiak");
        aktorzy.add("Bolesław Chrobry");
        Set<URL> linkiDoZwiastunow = new TreeSet<>();

        Produkt nowyProdukt = new Film(GeneratorNazw.wygenerujNazwe(), "Serial wyprodukowany przez blahblah blah Wysokie oceny, wysoka jakość, blah blah blah blah, Wspaniała gra aktorska blah blah blah blahblah.",
                new Date(2018,6,27), czasTrwania, "Polska", aktorzy, linkiDoZwiastunow, "Akcji"  );
        return nowyProdukt;
    }
}
