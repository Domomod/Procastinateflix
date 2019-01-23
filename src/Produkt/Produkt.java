package Produkt;

import Produkt.Generatory.MiesiacRok;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

public class Produkt implements Serializable {
    private String nazwa;
    private String opis;
    private Date dataProdukcji;
    private Duration czasTrwania;
    private int jakosc;
    static private int maksymalnaJakosc = 10;
    volatile private int cena = 10;

    volatile private  int ileRazyObejrzano = 0;
    transient volatile private XYChart.Series<String,Number> wykresOgladalnosci = new XYChart.Series<String, Number>();

    //To Do: Dystrybutor
    private String krajProdukcji;

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
        ileRazyObejrzano += 1;
    }

    synchronized public void zaktualizujWykres(){
        Platform.runLater(()->{
            wykresOgladalnosci.getData().add(new XYChart.Data<>(MiesiacRok.getMiesiacRok(), ileRazyObejrzano));
        });
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

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

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public static int getMaksymalnaJakosc() {
        return maksymalnaJakosc;
    }

    synchronized public XYChart.Series<String, Number> getWykresOgladalnosci() {
        return wykresOgladalnosci;
    }
}
