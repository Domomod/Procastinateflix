package Aktorzy;

import Produkt.Produkt;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class SimulationAPI {
    private static Symulacja symulacja = new Symulacja();

    static public WlascicielSerwisu getWlascicielSerwisu() {
        return symulacja.getWlascicielSerwisu();
    }

    static public ObservableList<Produkt> getProdukty(){
        return symulacja.getProdukty();
    }

    static public void wygenerujNowegoDystrybutora(){
        symulacja.getDystrybutorzy().dodajDystrybutora();
    }

    static public void wygenerujNowyProdukt(){
        symulacja.getDystrybutorzy().wygenerujNowyProduktNaZlecenieUżytkownika();
    }

    static public ObservableMap<Produkt, Umowa> getUmowy(){
        return symulacja.getUmowy();
    }

    static public ObservableList<Dystrybutor> getDystrybutorzy() {
        return symulacja.getDystrybutorzy().getDystrybutorzy();
    }

    static public void dodajProdukt(Produkt produkt, Umowa umowa){
        symulacja.dodajProdukt(produkt, umowa);
    }

    public static Produkt getLosowyProdukt() {return symulacja.getLosowyProdukt(); }

    public static void setSymulacja(Symulacja symulacja) {
        SimulationAPI.symulacja = symulacja;
    }
}
