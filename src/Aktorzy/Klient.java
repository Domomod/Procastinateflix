package Aktorzy;

import Produkt.Generatory.GeneratorImionNazwisk;
import Produkt.Produkt;
import com.sun.javaws.exceptions.InvalidArgumentException;
import sample.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import static java.lang.Thread.sleep;

public class Klient extends Osoba implements Runnable, Serializable {
    private String nazwa;
    private static volatile boolean endAllThreads = false;
    private static final Random rand = new Random();
    private List<Produkt> wykupioneProdukty = new ArrayList<>();
    private Umowa abonament;
    private boolean maAbonament = false;
    private int zadowolenie = 0;

    public Klient() {
        nazwa = GeneratorImionNazwisk.wygenerujNazwe();
    }

    /**
     * Watek klienta odpowiedzialny za okresowe podpisywanie nowych umow.
     */
    @Override
    public void run() {
        while(!ZbiorKlientow.isEndAllthread()){
            try {
                try {
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Produkt produkt;
                produkt = SimulationAPI.getLosowyProdukt();
                if (abonament == null) {
                    int decyzja = rand.nextInt(10);
                    if (decyzja < 8 || wykupioneProdukty.isEmpty()) {
                        if (super.getUmowy().size() == 0 || !wykupioneProdukty.contains(produkt)) {
                            Umowa.podpiszUmowe(this, produkt, SimulationAPI.getWlascicielSerwisu());
                            wykupioneProdukty.add(produkt);
                        }
                    } else {
                        int idx = rand.nextInt(wykupioneProdukty.size());
                        produkt = wykupioneProdukty.get(idx);
                    }
                }
                ogladajProdukt(produkt);
            }
            catch (Symulacja.BrakProduktowException e){
            }

            decydujOAbonamencie();
        }
    }

    /**
     * Egezkwuj umowy o wykupienie produktu lub abonamenty
     */
    @Override
    public void wyegzekwujUmowy(){
        for (Umowa umowa : super.getUmowy()) {
            umowa.wyegzekwuj();
        }
        if(abonament!=null) {
            abonament.wyegzekwuj();
        }
    }

    /**
     * Decyduj na podstawie swojego zadowolenia z systymu VOD i ceny jego abonamentu czy chcesz zakupic/zerwac/zmienic abonamet.
     */
    public void decydujOAbonamencie(){
        if(zadowolenie > 100 + 5 * Umowa.getRyczaltAbonamentuZwyklego()){
            if(zadowolenie > 500 + 5 * Umowa.getRyczaltAbonamentuPremium() && ( !maAbonament || abonamentJestPremium())){
                Umowa.podpiszAbonamentPremium(this, SimulationAPI.getWlascicielSerwisu());
            }else if (!maAbonament || abonamentJestZwykly()){
                Umowa.podpiszZwyklyAbonament(this, SimulationAPI.getWlascicielSerwisu());
            }
            if(!super.getUmowy().isEmpty())
                Umowa.zerwijUmowy(super.getUmowy());
        } else if (zadowolenie <= 100 && maAbonament){
            abonament.zerwijUmowe();
        }
    }

    private  boolean abonamentJestPremium(){
        return maAbonament && abonament.getRyczalt().equals(Umowa.getRyczaltAbonamentuPremium());
    }

    private boolean abonamentJestZwykly(){
        return maAbonament && abonament.getRyczalt().equals(Umowa.getRyczaltAbonamentuZwyklego());
    }

    public void wykupProdukt(){

    }
    public void ogladajProdukt(Produkt produkt){
        int jakosc = produkt.getJakosc();
        int maksymalneZawiedzenie = produkt.getMaksymalnaJakosc() - jakosc;
        int maksymalneZadowolenie = 1000;
        zadowolenie += (rand.nextInt(5 * jakosc) - maksymalneZawiedzenie) % maksymalneZadowolenie;
        produkt.obejrzyj();
    }

    synchronized static void endAllThreadsInNextCycle(){
        endAllThreads = true;
    }

    public void setAbonament(Umowa abonament) {
        this.abonament = abonament;
        if(abonament == null)
            maAbonament = false;
        else
            maAbonament = true;
    }

    public Umowa getAbonament() {
        return abonament;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getZadowolenie() {
        return zadowolenie;
    }
}
