package Produkt;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ProduktObjetyAbonamentem extends Produkt {
    public ProduktObjetyAbonamentem(String nazwa, String opis, Date dataProdukcji, Duration czasTrwania, String krajProdukcji) {
        super(nazwa, opis, dataProdukcji, czasTrwania, krajProdukcji);
    }
}
