package Produkt.Generatory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class GeneratorNazw {
    private Set<String> slowa;

    public GeneratorNazw(){
        slowa = new TreeSet<>();
    }

    public void zaladujSlowa(Scanner zrodlo){
        while( zrodlo.hasNext() ){
                slowa.add( zrodlo.next() );
        }
    }

    Set<String> pobiezZawieraneSlowa(){
        return slowa;
    }
}
