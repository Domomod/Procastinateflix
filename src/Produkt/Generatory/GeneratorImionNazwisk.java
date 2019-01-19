package Produkt.Generatory;

import java.util.Random;

public class GeneratorImionNazwisk {
    static public String wygenerujNazwe(){
        String s1 = GeneratorImionNazwisk.Imie.random();
        String s2 = GeneratorImionNazwisk.Nazwisko.random();
        String nazwa = s1 + " " + s2;
        return nazwa;
    }

    private enum Imie{
        one("Falibóg"),
        two("Stanibor"),
        thre("Wojuta"),
        four("Jur"),
        five("Niezamysł"),
        six("Radost"),
        seven("Mękosza"),
        eight("Nadziej"),
        nine("Mściwój"),
        ten("Sędziwuj"),
        eleven("Boguchwał"),
        twelve("Więcesław"),
        thirteen("Wrocisław");

        private final String slowo;
        Imie(final String s) {slowo = s;}
        static public String random(){
            Random generator = new Random();
            int index = generator.nextInt(GeneratorImionNazwisk.Imie.values().length);

            return GeneratorImionNazwisk.Imie.values()[index].slowo;
        }
    }

    private enum Nazwisko {
        one("Zygier"),
        two("Szuberlak"),
        thre("Suszek"),
        four("Ciułany"),
        five("Krzych"),
        six("Wochnik"),
        seven("Suszek"),
        eight("Wyrobek-Gaża"),
        nine("Głagolski"),
        ten("Cupryk"),
        eleven("Kowal"),
        twelve("Smarkiewicz"),
        thirteen("Skotarek");

        private final String slowo;
        Nazwisko(final String s) {slowo = s;}
        static public String random(){
            Random generator = new Random();
            int index = generator.nextInt(GeneratorImionNazwisk.Nazwisko.values().length);

            return GeneratorImionNazwisk.Nazwisko.values()[index].slowo;
        }
    }
}
