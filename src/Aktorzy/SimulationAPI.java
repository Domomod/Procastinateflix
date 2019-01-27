package Aktorzy;

import Produkt.Produkt;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.io.*;
import java.util.ArrayList;

/**
 * API symulacji, powstałe w celu ograniczneia bezpośredniej interferencji GUI z modelem biznesowym.
 */
public class SimulationAPI {
    private static Symulacja symulacja = new Symulacja();

    static public WlascicielSerwisu getWlascicielSerwisu() {
        return symulacja.getWlascicielSerwisu();
    }


    //----Symulacja----//
    public static void setSymulacja(Symulacja symulacja) {
        SimulationAPI.symulacja = symulacja;
    }

    /**
     * Serializuje symulacje do pliku binarnego
     *
     * @param nazwaPliku sciezka do Pliku
     */
    synchronized public static void serializacjia(String nazwaPliku) {
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

    /**
     * Deserializuje symulacje do pliku binarnego
     *
     * @param nazwaPliku sciezka do Pliku
     */
    synchronized public static void deserializacja(String nazwaPliku) {
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

    /**
     * Zmienia ceny abonamentow na nowe
     *
     * @param zwykly nowa cena zwyklego abonamentu
     * @param premium nowa cena abonamentu premium
     */
    public static void ustawCeneAbonamentu(Integer zwykly, Integer premium){
        Umowa.setRyczaltAbonamentuPremium(premium);
        Umowa.setRyczaltAbonamentuZwyklego(zwykly);
    }


    //----Produkty----//
    static public ObservableList<Produkt> getProdukty(){
        return symulacja.getProdukty();
    }

    /**
     * @return losowy Produkt oferowny przes sytem VOD
     * @throws Symulacja.BrakProduktowException
     */
    public static Produkt getLosowyProdukt() throws Symulacja.BrakProduktowException {return symulacja.getLosowyProdukt(); }

    /**
     * Generuje nowy produkt do rozpatrzenia przez wlasciciela
     */
    synchronized static public void wygenerujNowyProdukt(){
        symulacja.getDystrybutorzy().wygenerujNowyProduktNaZlecenieUżytkownika();
    }

    /**
     * Dodaje nowy produkt do Oferty VOD
     *
     * @param produkt
     */
    synchronized static public void dodajProdukt(Produkt produkt){
        symulacja.dodajProdukt(produkt);
    }


    /**
     * Zrywa umowy miedzy wlascicielem i dystrybutorem o dany produkt.
     * Usuwa produk z oferty VOD.
     *
     * @param produkt
     */
    synchronized static public void wycofajProduktZerwijUmowy(Produkt produkt) {

        Osoba odbiorca = null;
        Osoba platnik = null;
        Umowa umowaDoUsuniecia = null;

        for ( Umowa umowa : symulacja.getWlascicielSerwisu().getUmowy()) {
            if (umowa instanceof UmowaOProdukt){
                UmowaOProdukt umowaOProdukt = (UmowaOProdukt) umowa;

                odbiorca = umowaOProdukt.getOdbiorca();
                platnik = umowaOProdukt.getPlatnik();
                umowaDoUsuniecia = umowa;

                break;
            }
        }

        if(umowaDoUsuniecia != null){
            odbiorca.removeUmowy(umowaDoUsuniecia);
            platnik.removeUmowy(umowaDoUsuniecia);
        }
    }

    //----Dystrybutorzy----//
    static public ObservableList<Dystrybutor> getDystrybutorzy() {
        return symulacja.getDystrybutorzy().getDystrybutorzy();
    }

    /**
     * Dodaje nowego losowo wygenerowanego dystrybutora do symulacji.
     */
    synchronized static public void wygenerujNowegoDystrybutora(){
        symulacja.getDystrybutorzy().dodajDystrybutora();
    }



    //----Klienci----//
    static public ObservableList<Klient> getKlienci() {
        return symulacja.getKlienci().getKlienci();
    }

    /**
     * Dodaje nowego losowo wygenerowanego klienta do symulacji.
     */
    synchronized static public void wygenerujNowegoKlienta(){
        symulacja.getKlienci().dodajKlienta();
    }

    public static ObservableList<Umowa> getUmowy() {
        return symulacja.getWlascicielSerwisu().getUmowy();
    }

}
