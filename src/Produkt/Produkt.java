package Produkt;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

public class Produkt {
    private String nazwa;
    private String opis;
    private Date dataProdukcji;
    private Duration czasTrwania;
    private int jakosc;
    static private int maksymalnaJakosc;
    private int cena = 10;
    //To Do: Dystrybutor
    private String krajProdukcji;

    public Produkt(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataProdukcji = dataProdukcji;
        this.czasTrwania = czasTrwania;
        this.jakosc = 1 + (int)(Math.random() * 9);
        this.krajProdukcji = krajProdukcji;
    }

    public Produkt(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, int jakosc, String krajProdukcji){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataProdukcji = dataProdukcji;
        this.czasTrwania = czasTrwania;
        if(jakosc > 10 || jakosc < 1)
            throw new IllegalArgumentException( "jakosc filmu przedstawia się liczbą z zakresu <1, 10>" );
        this.jakosc = jakosc;
        this.krajProdukcji = krajProdukcji;
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
}
