package Produkt.Generatory;

import java.util.Random;

public class GeneratorFirmowychNazw {
    static public String wygenerujNazwe(){
        String s1 = Slowo1.random();
        String s2 = Slowo2.random();
        String nazwa = s1  + s2;
        return nazwa;
    }

    private enum Slowo1{
        one("Filmex"),
        two("Filmprom"),
        thre("Studio"),
        four("Manufaktura filmowa"),
        five("Browar"),
        six("Zygier"),
        seven("Hoplita"),
        eight("ZZZMovies"),
        nine("Studio");

        private final String slowo;
        Slowo1(final String s) {slowo = s;}
        static public String random(){
            Random generator = new Random();
            int index = generator.nextInt(Slowo1.values().length);

            return Slowo1.values()[index].slowo;
        }
    }

    private enum Slowo2 {
        one(" z.o.o"),
        two(" inc"),
        thre(" - spolka akcyjna"),
        four(" - spolka jawna"),
        five(" - bollywod's finest ");

        private final String slowo;
        Slowo2(final String s) {slowo = s;}
        static public String random(){
            Random generator = new Random();
            int index = generator.nextInt(Slowo2.values().length);

            return Slowo2.values()[index].slowo;
        }
    }
}
