package Produkt.Generatory;

public class MiesiacRok {

    private static int miesiac = 0;
    private static Integer rok = 2018;

    /**
     * @return zwraca w postaci lancucha znakow obecnie pamietany miesiac i rok. Format miesiac rok.
     */
    synchronized static public String getMiesiacRok(){
        String miesiac = Miesiac.terazMiesiac();
        String nazwa = miesiac + " " + rok.toString();
        return nazwa;
    }

    /**
     * Zapamietuje kolejny miesiac.
     */
    synchronized static public void miesiacMinal(){
        if(miesiac == 11)
            rok++;
        miesiac = (miesiac + 1) % 12;

    }

    private enum Miesiac{
        sty("Styczeń"),
        lut("Luty"),
        mar("Marzec"),
        kwi("Kwiecień"),
        maj("Maj"),
        cze("Czerwiec"),
        lip("Lipisec"),
        sie("Sierpień"),
        wrz("Wrzesień"),
        paz("Październik"),
        lis("Listopad"),
        gru("Grudzień");

        private final String slowo;
        Miesiac(final String s) {slowo = s;}

        static public String terazMiesiac(){
            return Miesiac.values()[miesiac].slowo;
        }

        static public void miesiacMija(){

        }
    }
}
