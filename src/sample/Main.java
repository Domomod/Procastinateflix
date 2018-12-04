package sample;

import Aktorzy.Dystrybutor;
import Produkt.Generatory.GeneratorNazw;
import Produkt.Produkt;
import Produkt.Film;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import sample.Controller;


public class Main extends Application {

    
    static private ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    static private Scene scenaGlowna;
    static private Scene scenaPropozycji;

    
    @Override
    public void start(Stage oknoGlowne) throws Exception{
        zaladujSceneGlowna();
        zaladujScenePropozyjci();
        noweOknoGlowne(oknoGlowne);
        dodajFilmy();


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
    
    private void zaladujScenePropozyjci() throws Exception{
        int szerokosc = 800;
        int wysokosc = 600;
        Parent root = FXMLLoader.load(getClass().getResource("PropozycjaProduktu.fxml"));
        scenaPropozycji = new Scene( root, szerokosc, wysokosc);
    }

    
    static public void noweOknoPropozycji() throws IOException {
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        dialog.setScene( scenaPropozycji );
        dialog.show();
    }

    
    private void dodajFilmy(){
        Dystrybutor dystrybutor = new Dystrybutor();
        for(int i = 0; i < 10; i++)
            produkty.add(dystrybutor.wydajProdukt());
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }

    
    public static ObservableList<Produkt> getProdukty(){
        return  produkty;
    }
}
