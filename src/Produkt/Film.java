package Produkt;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

public class Film extends ProduktObjetyAbonamentem {
    private Set<String> aktorzy;
    private Set<URL> linkiDoZwiastunow;
    private SimpleDateFormat terminDoOstatniegoObejrzenia;
    private String gatunek;


    public Film(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji,
                Set<String> aktorzy, Set<URL> linkiDoZwiastunow, String gatunek) {
        super(nazwa, opis, dataProdukcji, czasTrwania, krajProdukcji);
        this.aktorzy = aktorzy;
        this.linkiDoZwiastunow = linkiDoZwiastunow;
        this.terminDoOstatniegoObejrzenia = new SimpleDateFormat();
        this.gatunek = gatunek;
    }


    public Set<String> getAktorzy() {
        return aktorzy;
    }


    public Set<URL> getLinkiDoZwiastunow() {
        return linkiDoZwiastunow;
    }


    public SimpleDateFormat getTerminDoOstatniegoObejrzenia() {
        return terminDoOstatniegoObejrzenia;
    }


    public String getGatunek() {
        return gatunek;
    }
}

