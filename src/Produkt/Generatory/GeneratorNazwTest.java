package Produkt.Generatory;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class GeneratorNazwTest {


    @org.junit.jupiter.api.Test
    void zaladujSlowa() {
        GeneratorNazw generatorNazw = new GeneratorNazw();
        String imitacjaZawartosciPliku = "Ala Bartek Maciej Hryzantem";
        Scanner zrodlo = new Scanner(imitacjaZawartosciPliku);
        generatorNazw.zaladujSlowa(zrodlo);

        Set<String> zawieraneSlowa = new TreeSet<>();
            zawieraneSlowa = generatorNazw.pobiezZawieraneSlowa();
        Set<String> oczekiwaneSlowa = new TreeSet<>();
            oczekiwaneSlowa.add("Ala");
            oczekiwaneSlowa.add("Bartek");
            oczekiwaneSlowa.add("Maciej");
            oczekiwaneSlowa.add("Hryzantem");

            assert zawieraneSlowa.containsAll(oczekiwaneSlowa);
    }
}