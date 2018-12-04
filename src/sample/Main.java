package sample;

import Produkt.Produkt;
import Produkt.Film;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import sample.Controller;


public class Main extends Application {

    static private ObservableList<Produkt> produkty = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Dimension wymiaryMonitora = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setScene( new Scene( root, wymiaryMonitora.getWidth(), wymiaryMonitora.getHeight() ) );
        primaryStage.show();

        Duration czasTrwania = Duration.ZERO;
        czasTrwania.plusHours( 1 );
        czasTrwania.plusMinutes( 45 );
        Set<String> aktorzy = new TreeSet<>();
        aktorzy.add("Jacek Piotrowiak");
        aktorzy.add("MaFXMLciek Borowiak");
        aktorzy.add("Bolesław Chrobry");
        Set<URL> linkiDoZwiastunow = new TreeSet<>();

        Produkt przykladowyProdukt = new Film("Ślepnąć od Świateł", "Serial wyprodukowany przez blahblah blah Wysokie oceny, wysoka jakość, blah blah blah blah, Wspaniała gra aktorska blah blah blah blahblah.",
                new Date(2018,6,27), czasTrwania, "Polska", aktorzy, linkiDoZwiastunow, "Akcji"  );
        Produkt przykladowyProdukt1 = new Film("Ślepnąć od Haseł", "Serial wyprodukowany przez blahblah blah Wysokie oceny, wysoka jakość, blah blah blah blah, Wspaniała gra aktorska blah blah blah blahblah.",
                new Date(2018,6,27), czasTrwania, "Polska", aktorzy, linkiDoZwiastunow, "Akcji"  );
        Produkt przykladowyProdukt2 = new Film("Ślepnąć od Laser", "Serial wyprodukowany przez blahblah blah Wysokie oceny, wysoka jakość, blah blah blah blah, Wspaniała gra aktorska blah blah blah blahblah.",
                new Date(2018,6,27), czasTrwania, "Polska", aktorzy, linkiDoZwiastunow, "Akcji"  );
        Produkt przykladowyProdukt3 = new Film("Ślepnąć od Wafel", "Serial wyprodukowany przez blahblah blah Wysokie oceny, wysoka jakość, blah blah blah blah, Wspaniała gra aktorska blah blah blah blahblah.",
                new Date(2018,6,27), czasTrwania, "Polska", aktorzy, linkiDoZwiastunow, "Akcji"  );

        produkty.add(przykladowyProdukt);
        produkty.add(przykladowyProdukt1);
        produkty.add(przykladowyProdukt2);
        produkty.add(przykladowyProdukt3);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Produkt> getProdukty(){
        return  produkty;
    }
}
