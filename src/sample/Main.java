package sample;

import Aktorzy.Dystrybutor;
import Aktorzy.Umowa;
import Aktorzy.ZbiorDystrybutorow;
import Produkt.Produkt;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.Semaphore;

public class Main extends Application {

    static private Map<Produkt, Umowa> umowy = new LinkedHashMap<>();
    static private ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    static private Scene scenaGlowna;
    static private ZbiorDystrybutorow dystrybutorzy = new ZbiorDystrybutorow();
    
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

    static public void dodajProdukt(Produkt produkt, Umowa umowa){
        produkty.add(produkt);
        umowy.put(produkt, umowa);
    }

    public static void main(String[] args) {
        Thread watekDystrybutorow = new Thread(dystrybutorzy);
        watekDystrybutorow.start();
        launch(args);
    }

    
    public static ObservableList<Produkt> getProdukty(){
        return  produkty;
    }

    public static ZbiorDystrybutorow getDystrybutorzy() {
        return dystrybutorzy;
    }
}
