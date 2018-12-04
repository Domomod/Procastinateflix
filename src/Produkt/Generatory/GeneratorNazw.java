package Produkt.Generatory;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class GeneratorNazw {
    static public String wygenerujNazwe(){
        String przymiotnik = Przymiotnik.random().toString();
        String rzeczownik = Rzeczowniki.random().toString();
        String nazwa = przymiotnik + " " + rzeczownik;
        return nazwa;
    }

    private enum Przymiotnik{
        Wielki,
        Zielony,
        Tajemniczy,
        Srebrny,
        Dobry,
        Trudny,
        Czerwony,
        Zly,
        Szalony,
        Okrutny,
        Laskawy,
        Niezdarny;

        static public Przymiotnik random(){
            Random generator = new Random();
            int index = generator.nextInt(Przymiotnik.values().length);

            return Przymiotnik.values()[index];
        }
    }

    private enum Rzeczowniki {
        Alex,
        Diabeł,
        Gatsby,
        Kapelusznik,
        Kowal,
        Las,
        Nauczyciel,
        Piotr,
        Smok,
        Student,
        Tomasz,
        Wykladowca;

        static public Rzeczowniki random() {
            Random generator = new Random();
            int index = generator.nextInt(Rzeczowniki.values().length);

            return Rzeczowniki.values()[index];
        }
    }
}
