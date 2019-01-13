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
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Thread.sleep;

public class Dystrybutor implements Runnable{
    private String nazwa;
    private KontoBankowe kontoBankowe;
    private static boolean endAllThreads = false;
    private static Random rand = new Random();

    public void zaproponujUmowe() {
        Main.dodajPotencjalnyFilm(wydajProdukt());
    }

    private Produkt wydajProdukt(){
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

    synchronized static void endAllThreadsInNextCycle(){
        endAllThreads = true;
    }

    @Override
    public void run() {
        while(!endAllThreads) {

            synchronized (this.getClass()) {
                if (rand.nextInt(50) < 2) {
                    //zaproponujUmowe();
                }
            }

            try {
                sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
