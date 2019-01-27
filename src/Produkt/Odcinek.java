package Produkt;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class Odcinek implements Serializable {
    private String nazwa;

    public Odcinek(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}