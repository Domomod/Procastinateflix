package Aktorzy;

public class SimulationAPI {
    private static Symulacja symulacja = new Symulacja();

    static public WlascicielSerwisu getWlascicielSerwisu(){
        return Symulacja.getGracz();
    }
}
