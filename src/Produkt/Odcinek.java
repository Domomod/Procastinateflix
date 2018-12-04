package Produkt;

import java.text.SimpleDateFormat;
import java.time.Duration;

public class Odcinek {
    private SimpleDateFormat dataPremiery;
    private Duration czasTrwania;
    private String nazwa;

    public Odcinek(SimpleDateFormat dataPremiery, Duration czasTrwania, String nazwa) {
        this.dataPremiery = dataPremiery;
        this.czasTrwania = czasTrwania;
        this.nazwa = nazwa;
    }
}