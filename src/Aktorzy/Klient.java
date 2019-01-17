package Aktorzy;

import Produkt.Produkt;
import sample.Main;

import java.util.*;

import static java.lang.Thread.sleep;

public class Klient implements Runnable{
    private String nazwa;
    private KontoBankowe kontoBankowe = new KontoBankowe();
    private static volatile boolean endAllThreads = false;
    private static final Random rand = new Random();
    private List<Umowa> umowy = new ArrayList<>();
    private List<Produkt> wykupioneProdukty = new ArrayList<>();
    private Umowa abonament;
    private int zadowolenie = 0;

    @Override
    public void run() {
        while(!endAllThreads){
            try {
                sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Produkt produkt;
            produkt = SimulationAPI.getLosowyProdukt();
            if(abonament == null) {
                int decyzja = rand.nextInt(10);
                if (decyzja < 8) {
                    if (umowy.size() == 0 || !wykupioneProdukty.contains(produkt)) {
                        Umowa nowaUmowa = new Umowa(produkt.getCena(), kontoBankowe, SimulationAPI.getWlascicielSerwisu().getKontoBankowe());
                        umowy.add(nowaUmowa);
                        wykupioneProdukty.add(produkt);
                    }
                } else {
                    int idx = rand.nextInt(wykupioneProdukty.size());
                    produkt = wykupioneProdukty.get(idx);
                }
            }
            ogladajProdukt(produkt);


        }
    }

    public void decydujOAbonamencie(){
        if(zadowolenie > 100 && abonament == null){
            Umowa.podpiszZwyklyAbonament(this);
        }
        else if(zadowolenie > 500 && abonament == null){
            Umowa.podpiszAbonamentPremium(this);
        } else {
            abonament = null;
        }
    }

    public void wykupProdukt(){

    }
    public void ogladajProdukt(Produkt produkt){
        int jakosc = produkt.getJakosc();
        int maksymalneZawiedzenie = 5 * (Produkt.getMaksymalnaJakosc() - jakosc);
        zadowolenie += rand.nextInt(60 + 10 * jakosc) - maksymalneZawiedzenie;
    }

    synchronized static void endAllThreadsInNextCycle(){
        endAllThreads = true;
    }

    public KontoBankowe getKontoBankowe() {
        return kontoBankowe;
    }

    public void setAbonament(Umowa abonament) {
        this.abonament = abonament;
    }
}
