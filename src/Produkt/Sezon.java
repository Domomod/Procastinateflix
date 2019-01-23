package Produkt;

import java.io.Serializable;
import java.util.List;

public class Sezon implements Serializable {
    private List<Odcinek> odcinki;

    public Sezon(List<Odcinek> odcinki) {
        this.odcinki = odcinki;
    }
}
