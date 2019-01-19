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

    static public void wygenerujNowegoKlienta(){
        symulacja.getKlienci().dodajKlienta();
    }

    static public ObservableList<Dystrybutor> getDystrybutorzy() {
        return symulacja.getDystrybutorzy().getDystrybutorzy();
    }

    static public ObservableList<Klient> getKlienci() {
        return symulacja.getKlienci().getKlienci();
    }

    static public void dodajProdukt(Produkt produkt){
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
}
