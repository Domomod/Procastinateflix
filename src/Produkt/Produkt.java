package Produkt;

import Aktorzy.Umowa;
import Produkt.Generatory.GeneratorNazw;
import Produkt.Generatory.MiesiacRok;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Produkt implements Serializable {
    private String nazwa;
    private String opis;
    private Date dataProdukcji;
    private Duration czasTrwania;
    private int jakosc;
    static private int maksymalnaJakosc = 10;
    volatile private Integer cena = 10;

    volatile private  int ileRazyObejrzano = 0;
    transient volatile private XYChart.Series<String,Number> wykresOgladalnosci = new XYChart.Series<String, Number>();
    Map<String, Number> daneDoSerializacji = new LinkedHashMap<>();

    //To Do: Dystrybutor
    private String krajProdukcji;

    /**
     * Kunstruktor klasy bazowej Produkt. Nazwa, czas trwania, i jakosc sa wygenerowane automatycznie.
     */
    public Produkt(){
        czasTrwania = Duration.ZERO;
        int minut = 5 * (int)(Math.random() * 11);
        int godzin = (int)(Math.random() * 3);

        czasTrwania.plusHours( godzin );
        czasTrwania.plusMinutes( minut );

        jakosc = 1 + (int)(Math.random() * (maksymalnaJakosc - 1));

        nazwa = GeneratorNazw.wygenerujNazwe();

        opis = "ProcastinateFlix Originals. Bardzo wysoka jakość, doceniana przez światowych krytyków takich jak" +
                " Kryty Krytykalski i Nieznamsie Alesiewypowiem. ";
    }


    public Produkt(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataProdukcji = dataProdukcji;
        this.czasTrwania = czasTrwania;
        this.jakosc = 1 + (int)(Math.random() * (maksymalnaJakosc - 1));
        this.krajProdukcji = krajProdukcji;
    }

    public Produkt(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, int jakosc, String krajProdukcji){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataProdukcji = dataProdukcji;
        this.czasTrwania = czasTrwania;
        if(jakosc > maksymalnaJakosc || jakosc < 1)
            throw new IllegalArgumentException( "jakosc filmu przedstawia się liczbą z zakresu <1, 10>" );
        this.jakosc = jakosc;
        this.krajProdukcji = krajProdukcji;
    }

    synchronized public void obejrzyj(){
        synchronized (this){
            ileRazyObejrzano += 1;
        }

    }

    synchronized public void zaktualizujWykres(){
        synchronized (this){
            String data = MiesiacRok.getMiesiacRok();
            Platform.runLater(()->{
                wykresOgladalnosci.getData().add(new XYChart.Data<>(data, ileRazyObejrzano));
                daneDoSerializacji.put(data, ileRazyObejrzano);
            });
        }

    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void appendOpis(String s) {opis = opis + s;}

    public Date getDataProdukcji() {
        return dataProdukcji;
    }

    public Duration getCzasTrwania() {
        return czasTrwania;
    }

    public String getKrajProdukcji() {
        return krajProdukcji;
    }

    public int getJakosc() {
        return jakosc;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public static int getMaksymalnaJakosc() {
        return maksymalnaJakosc;
    }

    synchronized public XYChart.Series<String, Number> getWykresOgladalnosci() {
        return wykresOgladalnosci;
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        synchronized (this){
            stream.defaultReadObject();
            wykresOgladalnosci = new XYChart.Series<String, Number>();
            for (Map.Entry<String,Number> entry : daneDoSerializacji.entrySet() ) {
                XYChart.Data<String,Number> dane = new XYChart.Data<>(entry.getKey(), entry.getValue());
                wykresOgladalnosci.getData().add(dane);
            }
        }

    }
}
