package Produkt;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

public class Serial extends ProduktObjetyAbonamentem {
    private Set<Sezon> sezony;
    private String gatunek;

    public Serial(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji) {
        super(nazwa, opis, dataProdukcji, czasTrwania, krajProdukcji);
    }
}

