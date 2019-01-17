package sample;

import Aktorzy.*;
import Produkt.Produkt;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

    @Override
    public void start(Stage oknoGlowne) throws Exception{
        zaladujSceneGlowna();
        noweOknoGlowne(oknoGlowne);
    }

    private  void noweOknoGlowne(Stage oknoGlowne ) throws IOException {
        oknoGlowne.setTitle("Hello World");
        oknoGlowne.setScene(scenaGlowna);
        oknoGlowne.show();
    }

    private void zaladujSceneGlowna() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Dimension wymiaryMonitora = Toolkit.getDefaultToolkit().getScreenSize();
        scenaGlowna = new Scene( root, wymiaryMonitora.getWidth(), wymiaryMonitora.getHeight() );
    }

    public static void main(String[] args) {
        Thread nowyWatek = new Thread(symulacja);
        nowyWatek.start();
        SimulationAPI.setSymulacja(symulacja);
        launch(args);
    }
}
