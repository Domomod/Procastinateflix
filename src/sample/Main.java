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
