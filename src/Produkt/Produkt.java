package Produkt;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Produkt {
    private String nazwa;
    private String opis;
    private Date dataProdukcji;
    private Duration czasTrwania;
    //To Do: Dystrybutor
    private String krajProdukcji;

    public Produkt(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji){
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataProdukcji = dataProdukcji;
        this.czasTrwania = czasTrwania;
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
}
