package sample;

import Aktorzy.Dystrybutor;
import Aktorzy.ZbiorDystrybutorow;
import Produkt.Produkt;
import javafx.application.Application;
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
import java.util.Map;
import java.util.LinkedHashMap;

public class Main extends Application {


    static private Map<Stage, Produkt> potencjalneProdukty = new LinkedHashMap<>();
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
        wyswietlOknoPropozycji(oknoGlowne);
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

    
    static private Stage noweOknoPropozycji()  {
        VBox dialogVbox = new VBox(20);
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        dialog.setScene(scenaPropozycji);

        return dialog;
    }

    private static void wyswietlOknoPropozycji(Stage dialog) {
        dialog.show();
    }

    static synchronized public void dodajPotencjalnyFilm(Produkt produkt) {
        Stage oknoPropozycji = noweOknoPropozycji();
        potencjalneProdukty.put(oknoPropozycji, produkt);
        //wyswietlOknoPropozycji(oknoPropozycji);
    }

    synchronized private void rozpatrzPotencjalneFilmy() {
        for(Map.Entry<Stage, Produkt> pozycja : potencjalneProdukty.entrySet()){
            Stage okno = pozycja.getKey();

            wyswietlOknoPropozycji(pozycja.getKey());
        }
    }

    static synchronized public void przyjetoPropozycje(Stage oknoPropozycji){
        produkty.add( potencjalneProdukty.get(oknoPropozycji) );
        potencjalneProdukty.remove(oknoPropozycji);
    }

    static synchronized public void odrzuconoPropozycje(Stage oknoPropozycji){
        potencjalneProdukty.remove(oknoPropozycji);
    }

    private void dodajFilmy() {
    }
    
    
    public static void main(String[] args) {
        (new ZbiorDystrybutorow()).start();
        launch(args);
    }

    
    public static ObservableList<Produkt> getProdukty(){
        return  produkty;
    }
}
