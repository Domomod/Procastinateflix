package Aktorzy;

public class WlascicielSerwisu {
    Integer kwota_startowa = 1000;
    KontoBankowe kontoBankowe = new KontoBankowe(kwota_startowa);

    public WlascicielSerwisu() {
    }

    public KontoBankowe getKontoBankowe() {

        return kontoBankowe;
    }
}
