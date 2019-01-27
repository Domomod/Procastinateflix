package sample;

import Aktorzy.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

public class Main extends Application {
    static private Symulacja symulacja = new Symulacja();
    static private Scene scenaGlowna;
    static private Scene scenaFilm;
    static private Scene scenaSerial;
    static private Stage oknoGlowne;

    @Override
    public void start(Stage primaryStage) throws Exception{
        oknoGlowne = primaryStage;
        zaladujSceny();
        noweOknoGlowne(oknoGlowne);
        oknoGlowne.setOnCloseRequest(event -> {
            symulacja.endThreadInNextCycle();
        });
    }

    static public void wyswietlScenaGlowna(){
        oknoGlowne.setScene(scenaGlowna);
        oknoGlowne.setOnCloseRequest(event -> {
            symulacja.endThreadInNextCycle();
        });
    }

    static public void wyswietlSceneFilm() {
        oknoGlowne.setScene(scenaFilm);
        oknoGlowne.setOnCloseRequest(event -> {
            symulacja.endThreadInNextCycle();
        });
    }

    static public void wyswietlSceneSerial() {
        oknoGlowne.setScene(scenaSerial);
        oknoGlowne.setOnCloseRequest(event -> {
            symulacja.endThreadInNextCycle();
        });
    }

    private  void noweOknoGlowne(Stage oknoGlowne ) throws IOException {
        oknoGlowne.setTitle("Hello World");
        oknoGlowne.setScene(scenaGlowna);
        oknoGlowne.show();
    }

    private void zaladujSceny() throws Exception{
        Dimension wymiaryMonitora = Toolkit.getDefaultToolkit().getScreenSize();

        Parent glowna = FXMLLoader.load(getClass().getResource("Serial.fxml"));
        scenaGlowna = new Scene( glowna, wymiaryMonitora.getWidth(), wymiaryMonitora.getHeight() );

        Parent film = FXMLLoader.load(getClass().getResource("Film.fxml"));
        scenaFilm = new Scene( film, wymiaryMonitora.getWidth(), wymiaryMonitora.getHeight() );

        Parent serial = FXMLLoader.load(getClass().getResource("Serial.fxml"));
        scenaSerial = new Scene( serial, wymiaryMonitora.getWidth(), wymiaryMonitora.getHeight() );
    }

    public static void main(String[] args) {
        Thread nowyWatek = new Thread(symulacja);
        SimulationAPI.setSymulacja(symulacja);
        nowyWatek.start();
        launch(args);
    }
}
