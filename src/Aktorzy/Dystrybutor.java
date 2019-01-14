package Aktorzy;

import Produkt.Generatory.GeneratorFirmowychNazw;
import Produkt.Generatory.GeneratorNazw;
import Produkt.Produkt;
import Produkt.Film;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import sample.Main;

import java.net.URL;
import java.time.Duration;
import java.util.*;

import static java.lang.Thread.sleep;

public class Dystrybutor implements Runnable{
    private String nazwa;
    private KontoBankowe kontoBankowe = new KontoBankowe();
    private static volatile boolean endAllThreads = false;
    private static final Random rand = new Random();
    private List<Produkt> udostepnianeProdukty = new ArrayList<>();

    public Dystrybutor() {
        this.nazwa = GeneratorFirmowychNazw.wygenerujNazwe();
    }

    public void zaproponujUmowe() {
        Dystrybutor dystrybutor = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Produkt nowyProdukt = wydajProdukt();

                int jakoscProduktu = nowyProdukt.getJakosc();
                int ryczalt = jakoscProduktu * 100 + rand.nextInt(jakoscProduktu * 50);

                Umowa nowaUmowa = new Umowa(ryczalt, Main.getGracz().getKontoBankowe(), dystrybutor.getKontoBankowe());

                String opisWiadomości = nowyProdukt.getClass().getSimpleName() + ": " + nowyProdukt.getNazwa() + ", jakosc: " + nowyProdukt.getJakosc() + " za jedyne " + nowaUmowa.getRyczalt() + " zł miesięcznie. ";
                Alert alert = new Alert(Alert.AlertType.NONE, opisWiadomości, ButtonType.YES, ButtonType.NO);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setTitle( "Firma "+ nazwa +" chce podpisac nową umowę");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    Main.dodajProdukt(nowyProdukt, nowaUmowa);
                    udostepnianeProdukty.add(nowyProdukt);
                }
            }
        });
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
            try {
                sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (rand){
                if(rand.nextInt(200)<2)
                    zaproponujUmowe();
            }
        }
    }

    public String getNazwa() {
        return nazwa;
    }

    public KontoBankowe getKontoBankowe() {
        return kontoBankowe;
    }
}
