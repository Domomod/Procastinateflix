package Aktorzy;

import Produkt.Produkt;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.io.*;
import java.util.ArrayList;

public class SimulationAPI {
    private static Symulacja symulacja = new Symulacja();

    static public WlascicielSerwisu getWlascicielSerwisu() {
        return symulacja.getWlascicielSerwisu();
    }

    static public ObservableList<Produkt> getProdukty(){
        return symulacja.getProdukty();
    }

    synchronized static public void wygenerujNowegoDystrybutora(){
        symulacja.getDystrybutorzy().dodajDystrybutora();
    }

    synchronized static public void wygenerujNowyProdukt(){
        symulacja.getDystrybutorzy().wygenerujNowyProduktNaZlecenieUżytkownika();
    }

    synchronized static public void wygenerujNowegoKlienta(){
        symulacja.getKlienci().dodajKlienta();
    }

    static public ObservableList<Dystrybutor> getDystrybutorzy() {
        return symulacja.getDystrybutorzy().getDystrybutorzy();
    }

    static public ObservableList<Klient> getKlienci() {
        return symulacja.getKlienci().getKlienci();
    }

    synchronized static public void dodajProdukt(Produkt produkt){
        symulacja.dodajProdukt(produkt);
    }

    public void removeUmowy(Umowa umowa) {
        symulacja.getWlascicielSerwisu().getUmowy().remove(umowa);
    }

    public void removeUmowy(ObservableList<Umowa> umowy) {
        symulacja.getWlascicielSerwisu().getUmowy().removeAll(umowy);
    }

    public static Produkt getLosowyProdukt() throws Symulacja.BrakProduktowException {return symulacja.getLosowyProdukt(); }

    public static void setSymulacja(Symulacja symulacja) {
        SimulationAPI.symulacja = symulacja;
    }

    public static ObservableList<Umowa> getUmowy() {
        return symulacja.getWlascicielSerwisu().getUmowy();
    }

    synchronized public static void serializacjiaDoXML(String nazwaPliku) {
        try {
            ObjectOutputStream encoder = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(nazwaPliku)));
            encoder.writeObject(symulacja);
            encoder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*catch (IOException e) {
            wyswietlOknoBleduSerializacji(e);
        }*/
    }

    synchronized public static void deserializacjaXML(String nazwaPliku) {
        try {
            ObjectInputStream decoder = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(nazwaPliku)));
            symulacja = (Symulacja)decoder.readObject();
            decoder.close();
        } catch (IOException e) {
            wyswietlOknoBleduSerializacji(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void wyswietlOknoBleduSerializacji(Exception e){
        String opisWiadomości = "Coś jest nie tak z plikiem: " + e.getMessage();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, opisWiadomości);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle( "Bład Serializacij/Deserializacji");
        alert.showAndWait();
    }
}
